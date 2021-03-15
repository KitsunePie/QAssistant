package com.qnotifiedx.app.hook.base

import android.content.Context
import com.qnotifiedx.app.util.appContext

/**
 * Hook基类
 */
abstract class BaseHook {
    companion object {
        private const val HOOK_CFG_NAME = "qnotifiedx_prefs"

    }

    abstract val name: String

    protected var inited = false

    protected abstract fun init()

    open var enable: Boolean
        set(value) {
            appContext!!.getSharedPreferences(HOOK_CFG_NAME, Context.MODE_PRIVATE).edit()
                .putBoolean(this::class.java.name, value).apply()
        }
        get() {
            return appContext!!.getSharedPreferences(HOOK_CFG_NAME, Context.MODE_PRIVATE)
                .getBoolean(this::class.java.name, false)
        }
}