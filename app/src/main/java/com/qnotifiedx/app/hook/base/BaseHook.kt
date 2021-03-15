package com.qnotifiedx.app.hook.base

/**
 * Hook基类
 */
abstract class BaseHook {
    abstract val name: String

    open val desc: String = ""

    protected var inited = false

    protected abstract fun init()

    open var enable: Boolean = false

}