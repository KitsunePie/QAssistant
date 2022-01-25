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

package org.kitsunepie.qassistant.app.hook.base

import com.github.kyuubiran.ezxhelper.init.InitFields.moduleRes
import com.github.kyuubiran.ezxhelper.utils.Log
import org.kitsunepie.qassistant.R
import org.kitsunepie.qassistant.app.util.Process
import org.kitsunepie.qassistant.core.config.Config

/**
 * Hook基类
 */
abstract class BaseHook : IHookInfo {
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

    open var isActivated: Boolean
        get() = Config.sHookPref.getBoolean(javaClass.simpleName, false)
        set(value) {
            runCatching {
                if (needReboot && (isActivated != value)) Log.toast(moduleRes.getString(R.string.reboot_to_effect))
                Config.sHookPref.putBoolean(javaClass.simpleName, value)
            }
        }
}
