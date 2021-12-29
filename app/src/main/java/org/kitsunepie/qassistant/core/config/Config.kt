/*
 * QAssistant - An Xposed module for QQ/TIM
 * Copyright (C) 2021-2022
 * https://github.com/KitsunePie/QAssistant
 *
 * This software is non-free but opensource software: you can redistribute it
 * and/or modify it under the terms of the GNU Affero General Public License
 * as published by the Free Software Foundation and our eula published by us;
 *  either version 3 of the License, or any later version and our eula as published
 * by us.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * and eula along with this software.  If not, see
 * <https://www.gnu.org/licenses/>
 * <https://github.com/KitsunePie/QAssistant/blob/master/LICENSE.md>.
 */

package org.kitsunepie.qassistant.core.config

import android.annotation.SuppressLint
import com.github.kyuubiran.ezxhelper.init.InitFields.appContext
import com.tencent.mmkv.MMKV
import org.kitsunepie.qassistant.app.util.Natives
import org.kitsunepie.qassistant.app.util.SpProxy
import java.io.File
import java.io.IOException

object Config {

    @SuppressLint("UnsafeDynamicallyLoadedCode")
    fun init() {
        val ctx = appContext
        val mmkvDir = File(ctx.filesDir, "qa_mmkv")
        if (!mmkvDir.exists()) {
            mmkvDir.mkdirs()
        } else if (mmkvDir.isFile) {
            mmkvDir.delete()
            mmkvDir.mkdirs()
        }
        Natives.extractNativeLibrary(ctx, "mmkv")
        MMKV.initialize(ctx, mmkvDir.absolutePath) { s ->
            try {
                System.load(Natives.extractNativeLibrary(ctx, s).absolutePath)
            } catch (e: IOException) {
                throw UnsatisfiedLinkError("extract lib failed: $s").initCause(e)
            }
        }
    }

    private val initMmkv: Unit by lazy {
        init()
    }

    val sModulePref by lazy {
        initMmkv
        SpProxy(MMKV.mmkvWithID("QASettings", MMKV.MULTI_PROCESS_MODE))
    }
    val sHookPref by lazy {
        initMmkv
        SpProxy(MMKV.mmkvWithID("QAHooks", MMKV.MULTI_PROCESS_MODE))
    }
}
