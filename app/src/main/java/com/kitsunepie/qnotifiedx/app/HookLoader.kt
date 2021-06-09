package com.kitsunepie.qnotifiedx.app

import com.github.kyuubiran.ezxhelper.utils.Log
import com.kitsunepie.qnotifiedx.app.hook.base.BaseModuleInitHook

object HookLoader {
    @JvmStatic
    val init: Unit by lazy {
        doInit()
    }

    private fun doInit() {
        try {
            //加载模块初始化hooks
            BaseModuleInitHook.initHooks()
        } catch (e: Exception) {
            Log.e(e)
        }
    }
}