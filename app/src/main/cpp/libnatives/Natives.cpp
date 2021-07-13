/*
 * QNotified - An Xposed module for QQ/TIM
 * Copyright (C) 2019-2021 dmca@ioctl.cc
 * https://github.com/ferredoxin/QNotified
 *
 * This software is non-free but opensource software: you can redistribute it
 * and/or modify it under the terms of the GNU Affero General Public License
 * as published by the Free Software Foundation; either
 * version 3 of the License, or any later version and our eula as published
 * by ferredoxin.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * and eula along with this software.  If not, see
 * <https://www.gnu.org/licenses/>
 * <https://github.com/ferredoxin/QNotified/blob/master/LICENSE.md>.
 */

#include <errno.h>
#include <dlfcn.h>
#include "jni.h"
#include "memory.h"
#include "malloc.h"
#include "unistd.h"
#include "sys/mman.h"
#include "natives_utils.h"
#include <android/log.h>

#include "Natives.h"
#include "NativeMainHook.h"


uint32_t update_adler32(unsigned adler, const uint8_t *data, uint32_t len) {
    unsigned s1 = adler & 0xffffu;
    unsigned s2 = (adler >> 16u) & 0xffffu;

    while (len > 0) {
        /*at least 5550 sums can be done before the sums overflow, saving a lot of module divisions*/
        unsigned amount = len > 5550 ? 5550 : len;
        len -= amount;
        while (amount > 0) {
            s1 += (*data++);
            s2 += s1;
            --amount;
        }
        s1 %= 65521;
        s2 %= 65521;
    }
    return (s2 << 16u) | s1;
}

uint8_t *extractPayload(uint8_t *dex, int dexLength, int *outLength) {
    int chunkROff = readLe32(dex, dexLength - 4);
    if (chunkROff > dexLength) {
        *outLength = 0;
        return nullptr;
    }
    int base = dexLength - chunkROff;
    int size = readLe32(dex, base);
    if (size > dexLength) {
        *outLength = 0;
        return nullptr;
    }
    uint32_t flags = readLe32(dex, base + 4);
    uint32_t a32_got = readLe32(dex, base + 8);
    uint32_t extra = readLe32(dex, base + 12);
    if (flags != 0) {
        *outLength = 0;
        return nullptr;
    }
    uint32_t key = extra & 0xFFu;
    uint8_t *dat = (uint8_t *) malloc(size);
    if (key == 0) {
        memcpy(dat, dex + base + 16, size);
    } else {
        for (int i = 0; i < size; i++) {
            dat[i] = (uint8_t) (key ^ dex[base + 16 + i]);
        }
    }
    uint32_t a32 = update_adler32(1, dat, size);
    if (a32 != a32_got) {
        free(dat);
        *outLength = 0;
        return nullptr;
    }
    return dat;
}

static int64_t sBuildTimestamp = -2;

static const int DEX_MAX_SIZE = 12 * 1024 * 1024;

jlong doGetBuildTimestamp(JNIEnv *env, jclass clazz) {
    if (sBuildTimestamp != -2)return sBuildTimestamp;
    jclass cl_Class = env->FindClass("java/lang/Class");
    jobject loader = env->CallObjectMethod(clazz,
                                           env->GetMethodID(cl_Class, "getClassLoader",
                                                            "()Ljava/lang/ClassLoader;"));
    jobject eu = env->CallObjectMethod(loader,
                                       env->GetMethodID(env->FindClass("java/lang/ClassLoader"),
                                                        "findResources",
                                                        "(Ljava/lang/String;)Ljava/util/Enumeration;"),
                                       env->NewStringUTF("classes.dex"));
    if (eu == nullptr) {
        return -2;
    }
    jclass cl_Enum = env->FindClass("java/util/Enumeration");
    jclass cl_Url = env->FindClass("java/net/URL");
    jmethodID hasMoreElements = env->GetMethodID(cl_Enum, "hasMoreElements", "()Z");
    jmethodID nextElement = env->GetMethodID(cl_Enum, "nextElement", "()Ljava/lang/Object;");
    jbyteArray buf = env->NewByteArray(2048);
    jmethodID openStream = env->GetMethodID(cl_Url, "openStream", "()Ljava/io/InputStream;");
    jclass cIs = env->FindClass("java/io/InputStream");
    jmethodID is_read = env->GetMethodID(cIs, "read", "([B)I");
    jmethodID is_close = env->GetMethodID(cIs, "close", "()V");
    jmethodID toString = env->GetMethodID(env->FindClass("java/lang/Object"), "toString",
                                          "()Ljava/lang/String;");
    if (env->ExceptionCheck()) {
        return -2;
    }
    uint8_t *dex = (uint8_t *) (malloc(DEX_MAX_SIZE));
    if (dex == nullptr) {
        __android_log_print(ANDROID_LOG_ERROR, "QAdump", "unable to allocate dex buffer\n");
        return -2;
    }
    int count = 0;
    while (env->CallBooleanMethod(eu, hasMoreElements)) {
        jobject url = env->CallObjectMethod(eu, nextElement);
        if (url == nullptr) {
            continue;
        }
        count++;
        jobject is = env->CallObjectMethod(url, openStream);
        if (is == nullptr) {
            jthrowable ex = env->ExceptionOccurred();
            if (ex != nullptr) {
                jstring jst = (jstring) env->CallObjectMethod(ex, toString);
                const char *errstr = env->GetStringUTFChars(jst, nullptr);
                __android_log_print(ANDROID_LOG_ERROR, "QAdump", "dex openStream error: %s\n",
                                    errstr);
                env->ReleaseStringUTFChars(jst, errstr);
            }
            env->ExceptionClear();
            continue;
        }
        int length = 0;
        int ri = 0;
        while (!env->ExceptionCheck() && (ri = env->CallIntMethod(is, is_read, buf)) > 0) {
            if (length + ri < DEX_MAX_SIZE) {
                env->GetByteArrayRegion(buf, 0, ri, (jbyte *) (dex + length));
            }
            length += ri;
        }
        if (env->ExceptionCheck()) {
            jthrowable ex = env->ExceptionOccurred();
            if (ex != nullptr) {
                jstring jst = (jstring) env->CallObjectMethod(ex, toString);
                const char *errstr = env->GetStringUTFChars(jst, nullptr);
                __android_log_print(ANDROID_LOG_ERROR, "QAdump", "dex read error: %s\n",
                                    errstr);
                env->ReleaseStringUTFChars(jst, errstr);
            }
            env->ExceptionClear();
            env->CallVoidMethod(is, is_close);
            env->ExceptionClear();
            continue;
        }
        env->CallVoidMethod(is, is_close);
        env->ExceptionClear();
        {
            //parse [dex, dex+length]
            if (length < 128 * 1024) {
                continue;
            }
            int tailLength = 0;
            uint8_t *tail = extractPayload(dex, length, &tailLength);
            if (tail != nullptr) {
                uint64_t time = 0;
                for (int i = 0; i < 8; i++) {
                    time |= ((uint64_t) ((((uint64_t) tail[i]) & ((uint64_t) 0xFFLLu)))
                            << (8u * i));
                }
                sBuildTimestamp = time;
                free(tail);
                free(dex);
                return time;
            }
        }
    }
    free(dex);
    dex = nullptr;
    if (count == 0) {
        __android_log_print(ANDROID_LOG_ERROR, "QAdump", "getBuildTimestamp/E urls.size == 0\n");
        return -2;
    }
    sBuildTimestamp = -1;
    return sBuildTimestamp;
}
