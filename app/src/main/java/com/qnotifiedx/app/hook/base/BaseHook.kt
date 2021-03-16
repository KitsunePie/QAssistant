package com.qnotifiedx.app.hook.base

import com.qnotifiedx.app.util.SpProxy
import com.qnotifiedx.core.processctrl.Process
import com.tencent.mmkv.MMKV

/**
 * Hook基类
 */
abstract class BaseHook {
    private val sp by lazy {
        SpProxy(MMKV.mmkvWithID("HookConfig", MMKV.MULTI_PROCESS_MODE))
    }

    //进程控制
    protected open val targetProc: Array<Process> = arrayOf(Process.PROC_MAIN)

    //Hook名称
    abstract val name: String

    //Hook说明
    open val desc: String = ""

    //是否已加载
    protected var inited = false

    //Hook执行过程
    protected abstract fun init()

    //是否开启
    open var enable: Boolean
        get() = sp.getBoolean(javaClass.simpleName, false)
        set(value) {
            sp.putBoolean(javaClass.simpleName, value)
        }

}