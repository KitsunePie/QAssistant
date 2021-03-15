package com.qnotifiedx.app.hook.base

import com.qnotifiedx.app.util.Log

/**
 * Delay Hooks
 * 在模块获取到宿主全局Context后延迟执行的Hook将继承于此类
 */
abstract class BaseDelayHook : BaseHook() {

    companion object {
        private val delayHooks =
            com.qnotifiedx.gen.AnnotatedDelayItemList.getAnnotatedDelayItemClassList()
                .toTypedArray()

        fun initHooks() {
            for (h in delayHooks) {
                if (!h.inited) {
                    h.inited = true
                    h.init()
                    Log.i("Initialized Delay hook: ${h.javaClass.name}")
                }
            }
        }
    }
}