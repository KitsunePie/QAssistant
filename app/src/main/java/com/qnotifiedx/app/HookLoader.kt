package com.qnotifiedx.app

import com.qnotifiedx.app.hook.base.BaseDelayHook
import com.qnotifiedx.app.hook.base.BaseNormalHook
import com.qnotifiedx.app.util.Log

object HookLoader {
    val init: Unit by lazy {
        doInit()
    }

    private fun doInit() {
        try {
            BaseNormalHook.initHooks()
            Log.i("Module first initialization successful.")
            doSecondInit()
        } catch (e: Exception) {
            Log.e(e)
        }
    }

    private fun doSecondInit() {
        try {
            BaseDelayHook.initHooks()
            Log.i("Module second initialization successful.")
        } catch (e: Exception) {
            Log.e(e)
        }
    }
}