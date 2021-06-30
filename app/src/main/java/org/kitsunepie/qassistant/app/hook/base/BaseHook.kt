package org.kitsunepie.qassistant.app.hook.base

import com.github.kyuubiran.ezxhelper.init.InitFields.moduleRes
import com.github.kyuubiran.ezxhelper.utils.Log
import org.kitsunepie.qassistant.R
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
            return false
        }

    //Hook执行过程
    fun init()

    fun isActivated(): Boolean = Config.sHookPref.getBoolean(javaClass.simpleName, false)
    fun setActivated(value: Boolean) {
        try {
            if (needReboot && (isActivated() != value)) Log.toast(moduleRes.getString(R.string.reboot_to_effect))
            Config.sHookPref.putBoolean(javaClass.simpleName, value)
        } catch (e: Exception) {
            //ignore
        }
    }
}