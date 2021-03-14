package com.qnotifiedx.app

import com.qnotifiedx.app.hook.base.BaseNormalHook
import com.qnotifiedx.app.hook.base.moduleinit.LateinitHook
import com.qnotifiedx.app.hook.base.moduleinit.ModuleEntry
import com.qnotifiedx.app.util.Log

object HookLoader {
    val init: Unit by lazy {
        doInit()
    }

    private fun doInit() {
        try {
            //获取Context以及入口的hook
            LateinitHook.init()
            ModuleEntry.init()
            //加载普通hooks
            BaseNormalHook.initHooks()
            Log.i("Module first initialization successful.")
        } catch (e: Exception) {
            Log.e(e)
        }
    }
}