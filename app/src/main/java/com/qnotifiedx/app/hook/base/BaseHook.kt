package com.qnotifiedx.app.hook.base

abstract class BaseHook {
    open var enable = false
    protected var inited = false

    protected abstract fun init()
}