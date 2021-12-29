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

import com.github.kyuubiran.ezxhelper.utils.Log
import org.kitsunepie.qassistant.app.HookInit
import org.kitsunepie.qassistant.app.hook.moduleinit.GetApplication
import org.kitsunepie.qassistant.app.hook.moduleinit.ModuleEntry
import org.kitsunepie.qassistant.app.util.ProcessInfo.isCurrentProc
import org.kitsunepie.qassistant.gen.normalHooks

/**
 * 模块初始化相关的Hook
 */
object HookInitializer {
    private val moduleHooks = arrayOf(
        GetApplication,
        ModuleEntry
    )

    fun initModuleHooks() {
        moduleHooks.forEach { h ->
            if (h.isInited || HookInit.processName != HookInit.packageName) return@forEach
            try {
                h.init()
                h.isInited = true
                Log.i("Inited module hook: ${h.javaClass.name}")
            } catch (thr: Throwable) {
                Log.e(thr, "Init failed module hook: ${h.javaClass.name}")
                throw thr
            }
        }
    }

    fun initNormalHooks() {
        normalHooks.forEach hook@{ h ->
            if (h.isInited) return@hook
            h.targetProc.forEach proc@{ p ->
                if (!p.isCurrentProc) return@proc
                try {
                    h.init()
                    h.isInited = true
                    Log.i("Init normal hook: ${h.javaClass.name}")
                } catch (thr: Throwable) {
                    Log.e(thr, "Init failed normal hook: ${h.javaClass.name}")
                }
            }
        }
    }
}
