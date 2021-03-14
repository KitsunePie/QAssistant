package com.qnotifiedx.app.hook.base

/**
 * Hook基类
 */
abstract class BaseHook {
    protected open var enable = false
    protected var inited = false

    protected abstract fun init()
}