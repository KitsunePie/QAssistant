package com.qnotifiedx.app.hook.base

import com.qnotifiedx.app.hook.delay.PreventDiyCardLoad
import com.qnotifiedx.app.util.Log

/**
 * Delay Hooks
 * 在模块加载完毕后延迟执行的Hook将继承于此类
 */
abstract class BaseDelayHook {
    protected open var enable = false
    protected var inited = false

    companion object {
        private val delayHooks = arrayOf<BaseDelayHook>(
            PreventDiyCardLoad,
        )

        fun initHooks() {
            for (h in delayHooks) {
                if (h.enable && !h.inited) {
                    h.inited = true
                    h.init()
                    Log.i("Inited Delay hook:${h.javaClass.name}")
                }
            }
        }
    }

    protected abstract fun init()
}