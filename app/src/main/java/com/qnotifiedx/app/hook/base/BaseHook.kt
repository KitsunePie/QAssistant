package com.qnotifiedx.app.hook.base

import com.qnotifiedx.app.hook.normal.GetAppContext
import com.qnotifiedx.app.hook.normal.ModuleEntry
import com.qnotifiedx.app.util.Log

abstract class BaseHook {
    protected open var enable = false
    protected var inited = false

    companion object {
        private val normalHooks = arrayOf(
            GetAppContext,
            ModuleEntry,
        )

        fun initHooks() {
            for (h in normalHooks) {
                if (h.enable && !h.inited) {
                    h.inited = true
                    h.init()
                    Log.i("Inited Normal hook:${h.javaClass.name}")
                }
            }
        }
    }

    protected abstract fun init()
}