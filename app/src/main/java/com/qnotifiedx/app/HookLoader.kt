package com.qnotifiedx.app

import com.qnotifiedx.app.hook.base.BaseModuleInit
import com.qnotifiedx.app.util.Log

object HookLoader {
    val init: Unit by lazy {
        doInit()
    }

    private fun doInit() {
        try {
            //加载普通hooks
            BaseModuleInit.initHooks()
            Log.i("Module first initialization successful.")
        } catch (e: Exception) {
            Log.e(e)
        }
    }
}