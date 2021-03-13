package com.qnotifiedx.app.hook.base

import com.qnotifiedx.app.util.Log

/**
 * Normal Hooks
 * 在模块加载完毕后立刻就会执行的Hook将继承于此类
 */
abstract class BaseNormalHook {
    protected open var enable = false
    protected var inited = false

    companion object {
        private val normalHooks = com.qnotifiedx.gen.AnnotatedNormalItemList.getAnnotatedNormalItemClassList().toTypedArray()

        fun initHooks() {
            for (h in normalHooks) {
                if (!h.inited) {
                    h.inited = true
                    h.init()
                    Log.i("Initialized Normal hook: ${h.javaClass.name}")
                }
            }
        }
    }

    protected abstract fun init()
}