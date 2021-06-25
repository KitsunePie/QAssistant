package org.kitsunepie.qassistant.app.hook.base

import com.tencent.mmkv.MMKV
import org.kitsunepie.qassistant.app.util.SpProxy
import org.kitsunepie.qassistant.core.processctrl.Process
import org.kitsunepie.qassistant.core.processctrl.Process.PROC_MAIN

/**
 * Hook基类
 */
abstract class BaseHook {
    private val sp by lazy {
        SpProxy(MMKV.mmkvWithID("HookConfig", MMKV.MULTI_PROCESS_MODE))
    }

    //进程控制
    protected open val targetProc: Array<Process> =
        arrayOf(PROC_MAIN)

    //Hook名称
    abstract val name: String

    //Hook说明
    open val desc: String = ""

    //是否已加载
    protected var isInit = false

    //是否重启生效
    protected var needReboot = false

    //Hook执行过程
    protected abstract fun init()

    //是否开启
    open var isEnable: Boolean
        get() = sp.getBoolean(javaClass.simpleName, false)
        set(value) {
            sp.putBoolean(javaClass.simpleName, value)
        }

}