package com.qnotifiedx.app.hook.base

import com.qnotifiedx.app.util.Log
import com.qnotifiedx.app.util.SpProxy
import com.tencent.mmkv.MMKV

/**
 * Hook基类
 */
abstract class BaseHook {
    private val sp by lazy {
        SpProxy(MMKV.mmkvWithID("HookConfig", MMKV.MULTI_PROCESS_MODE))
    }

    abstract val name: String

    open val desc: String = ""

    protected var inited = false

    protected abstract fun init()

    open var enable: Boolean
    get() = sp.getBoolean(javaClass.simpleName, false)
    set(value) {
        sp.putBoolean(javaClass.simpleName, value)
    }

}