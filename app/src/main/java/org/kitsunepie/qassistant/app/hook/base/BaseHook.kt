package org.kitsunepie.qassistant.app.hook.base

import com.github.kyuubiran.ezxhelper.init.InitFields.moduleRes
import com.github.kyuubiran.ezxhelper.utils.Log
import org.kitsunepie.qassistant.R
import org.kitsunepie.qassistant.app.util.Process
import org.kitsunepie.qassistant.core.config.Config

/**
 * Hook基类
 */
abstract class BaseHook {
    //进程控制
    val targetProc: Array<Process>
        get() = arrayOf(Process.PROC_MAIN)

    //是否已加载
    var isInited: Boolean = false

    //是否重启生效
    open val needReboot: Boolean
        get() = false


    //Hook执行过程
    abstract fun init()

    open fun isActivated(): Boolean = Config.sHookPref.getBoolean(javaClass.simpleName, false)

    open fun setActivated(value: Boolean) {
        runCatching {
            if (needReboot && (isActivated() != value)) Log.toast(moduleRes.getString(R.string.reboot_to_effect))
            Config.sHookPref.putBoolean(javaClass.simpleName, value)
        }
    }
}
