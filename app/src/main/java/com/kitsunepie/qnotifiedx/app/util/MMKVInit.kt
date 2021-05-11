package com.kitsunepie.qnotifiedx.app.util

import android.annotation.SuppressLint
import com.github.kyuubiran.ezxhelper.init.InitFields.appContext
import com.tencent.mmkv.MMKV
import java.io.File
import java.io.IOException

object MMKVInit {

    @SuppressLint("UnsafeDynamicallyLoadedCode")
    fun init() {
        val ctx = appContext
        val mmkvDir = File(appContext.filesDir, "qnx_mmkv")
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
}