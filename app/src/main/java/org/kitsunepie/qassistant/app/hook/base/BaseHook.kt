package org.kitsunepie.qassistant.app.hook.base

import org.kitsunepie.qassistant.core.config.Config
import org.kitsunepie.qassistant.core.processctrl.Process

/**
 * Hook基类
 */
interface BaseHook {
    //进程控制
    val targetProc: Array<Process>
        get() {
            return arrayOf(Process.PROC_MAIN)
        }

    //是否已加载
    var isInit: Boolean

    //是否重启生效
    val needReboot: Boolean
        get() {
            return true
        }

    //Hook执行过程
    fun init()

    fun isActivated(): Boolean = Config.sHookPref.getBoolean(javaClass.simpleName, false)
    fun setActivated(value: Boolean) = Config.sHookPref.putBoolean(javaClass.simpleName, value)
}