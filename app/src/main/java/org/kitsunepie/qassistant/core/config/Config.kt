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

    val initMmkv: Unit by lazy {
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