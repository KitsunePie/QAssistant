package com.qnotifiedx.app.hook.base

import com.qnotifiedx.app.util.Log
import com.qnotifiedx.core.processctrl.ProcessInfo.isCurrentProc

/**
 * Delay Hooks
 * 在模块获取到宿主全局Context后执行的Hook将继承于此类
 */
abstract class BaseNormalHook : BaseHook() {

    companion object {
        private val delayHooks =
            com.qnotifiedx.gen.AnnotatedNormalItemList.getAnnotatedNormalItemClassList()
                .toTypedArray()

        fun initHooks() {
            for (h in delayHooks) {
                if (!h.inited) {
                    for (proc in h.targetProc) {
                        if (proc.isCurrentProc) {
                            try {
                                h.init()
                                h.inited = true
                                Log.i("Initialized delay hook: ${h.javaClass.name}")
                            } catch (e: Exception) {
                                Log.i("Initialization failure delay hook: ${h.javaClass.name}")
                            }
                        }
                    }
                }
            }
        }
    }
}