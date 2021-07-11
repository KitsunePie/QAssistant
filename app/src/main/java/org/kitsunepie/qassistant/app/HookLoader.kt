package org.kitsunepie.qassistant.app

import com.github.kyuubiran.ezxhelper.utils.Log
import org.kitsunepie.qassistant.app.hook.base.HookInitializer

object HookLoader {
    @JvmStatic
    val init: Unit by lazy {
        doInit()
    }

    private fun doInit() {
        try {
            //加载模块初始化hooks
            HookInitializer.initModuleHooks()
        } catch (e: Exception) {
            Log.e(e)
        }
    }
}