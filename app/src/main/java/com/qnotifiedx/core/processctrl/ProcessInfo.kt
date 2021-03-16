package com.qnotifiedx.core.processctrl

import com.qnotifiedx.app.HookInit
import com.qnotifiedx.app.util.Info.HOST_PACKAGE_NAME

enum class Process {
    PROC_PEAK,
    PROC_MAIN,
    PROC_MSF,
    PROC_TOOL,
    PROC_MINI,
    PROC_QZONE,
    PROC_ANY
}

object ProcessInfo {
    //当前的进程名称
    val procName by lazy {
        HookInit.processName
    }

    //是否为当前进程
    val Process.isCurrentProc: Boolean
        get() = if (this == Process.PROC_ANY) true else this == currentProc

    val currentProc: Process = when (procName) {
        "${HOST_PACKAGE_NAME}:peak" -> Process.PROC_PEAK               //图片进程
        HOST_PACKAGE_NAME -> Process.PROC_MAIN                         //主进程
        "${HOST_PACKAGE_NAME}:MSF" -> Process.PROC_MSF                 //MSF
        "${HOST_PACKAGE_NAME}:tool" -> Process.PROC_TOOL               //工具
        "${HOST_PACKAGE_NAME}:mini", "${HOST_PACKAGE_NAME}:mini1",     //小程序
        "${HOST_PACKAGE_NAME}:mini2", "${HOST_PACKAGE_NAME}:mini3",    //小程序
        "${HOST_PACKAGE_NAME}:mini4" -> Process.PROC_MINI              //小程序
        "${HOST_PACKAGE_NAME}:qzone" -> Process.PROC_QZONE             //空间
        else -> Process.PROC_ANY                                       //所有进程
    }
}