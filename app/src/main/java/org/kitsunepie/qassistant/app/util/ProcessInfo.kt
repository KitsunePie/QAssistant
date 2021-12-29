/*
 * QAssistant - An Xposed module for QQ/TIM
 * Copyright (C) 2021-2022
 * https://github.com/KitsunePie/QAssistant
 *
 * This software is non-free but opensource software: you can redistribute it
 * and/or modify it under the terms of the GNU Affero General Public License
 * as published by the Free Software Foundation and our eula published by us;
 *  either version 3 of the License, or any later version and our eula as published
 * by us.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * and eula along with this software.  If not, see
 * <https://www.gnu.org/licenses/>
 * <https://github.com/KitsunePie/QAssistant/blob/master/LICENSE.md>.
 */

package org.kitsunepie.qassistant.app.util

import org.kitsunepie.qassistant.app.HookInit
import org.kitsunepie.qassistant.app.util.Info.HOST_PACKAGE_NAME

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
    val currentProcName by lazy {
        HookInit.processName
    }

    //是否为当前进程
    val Process.isCurrentProc: Boolean
        get() = if (this == Process.PROC_ANY) true else this == currentProc

    val currentProc: Process = when (currentProcName) {
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
