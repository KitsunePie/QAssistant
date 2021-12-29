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

package org.kitsunepie.qassistant.app.util

import android.content.Context
import android.os.Build
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

object Natives {
    /**
     * 导出或更新Native支持库到"qa_dyn_lib"目录
     *
     * @param libraryName 支持库名字，不含 "lib" or ".so", 例如： "mmkv"
     */
    @Throws(IOException::class)
    fun extractNativeLibrary(ctx: Context, libraryName: String): File {
        @Suppress("DEPRECATION") val abi = Build.CPU_ABI
        val soName =
            "lib" + libraryName + ".so." + org.kitsunepie.qassistant.BuildConfig.VERSION_NAME + "." + abi
        val dir = File(ctx.filesDir, "qa_dyn_lib")
        if (!dir.isDirectory) {
            if (dir.isFile) {
                dir.delete()
            }
            dir.mkdir()
        }
        val soFile = File(dir, soName)
        if (!soFile.exists()) {
            val stream = Natives::class.java.classLoader
                ?.getResourceAsStream("lib/$abi/lib$libraryName.so")
                ?: throw UnsatisfiedLinkError("Unsupported ABI: $abi")
            //清理旧文件
            dir.list()?.forEach {
                if (it.startsWith("lib" + libraryName + "_")
                    || it.startsWith("lib$libraryName.so")
                ) {
                    File(dir, it).delete()
                }
            }
            //导出 so 文件
            soFile.createNewFile()
            val fout = FileOutputStream(soFile)
            val buf = ByteArray(1024)
            var i: Int
            while (stream.read(buf).also { i = it } > 0) {
                fout.write(buf, 0, i)
            }
            stream.close()
            fout.flush()
            fout.close()
        }
        return soFile
    }
}
