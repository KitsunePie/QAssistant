package com.kitsunepie.qnotifiedx.app.hook.base

import com.github.kyuubiran.ezxhelper.utils.Log
import com.kitsunepie.qnotifiedx.core.processctrl.ProcessInfo.isCurrentProc
import com.kitsunepie.qnotifiedx.gen.DelayHooks

/**
 * Delay Hooks
 * 在模块获取到宿主全局Context后执行的Hook将继承于此类
 */
abstract class BaseNormalHook : BaseHook() {

    companion object {
        private val normalHooks = DelayHooks.getAnnotatedItemClassList()

        fun initHooks() {
            for (h in normalHooks) {
                if (h.isInited) continue
                for (proc in h.targetProc) {
                    if (!proc.isCurrentProc) continue
                    try {
                        h.init()
                        h.isInited = true
                        Log.i("Initialized normal hook: ${h.javaClass.name}")
                    } catch (thr: Throwable) {
                        Log.t(thr, "Initialization failure normal hook: ${h.javaClass.name}")
                    }
                }
            }
        }
    }
}