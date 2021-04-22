package com.kitsunepie.qnotifiedx.app

import com.github.kyuubiran.ezxhelper.utils.Log
import com.kitsunepie.qnotifiedx.app.hook.base.BaseModuleInit

object HookLoader {
    val init: Unit by lazy {
        doInit()
    }

    private fun doInit() {
        try {
            //加载普通hooks
            Log.i("Do init")
            BaseModuleInit.initHooks()
            Log.i("Module first initialization successful.")
        } catch (e: Exception) {
            Log.e(e)
        }
    }
}