package com.kitsunepie.qnotifiedx.app.util

import android.content.Context
import android.os.Build
import com.qnotifiedx.app.BuildConfig
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

object Natives {
    /**
     * 导出或更新Native支持库到"qnx_dyn_lib"目录
     *
     * @param libraryName 支持库名字，不含 "lib" or ".so", 例如： "mmkv"
     */
    @Throws(IOException::class)
    fun extractNativeLibrary(ctx: Context, libraryName: String): File {
        @Suppress("DEPRECATION") val abi = Build.CPU_ABI
        val soName = "lib" + libraryName + ".so." + BuildConfig.VERSION_NAME + "." + abi
        val dir = File(ctx.filesDir, "qnx_dyn_lib")
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