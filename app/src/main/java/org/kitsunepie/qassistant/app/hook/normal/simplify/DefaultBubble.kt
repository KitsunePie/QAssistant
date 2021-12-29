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

package org.kitsunepie.qassistant.app.hook.normal.simplify

import com.github.kyuubiran.ezxhelper.init.InitFields
import com.github.kyuubiran.ezxhelper.init.InitFields.appContext
import com.github.kyuubiran.ezxhelper.utils.Log
import com.github.kyuubiran.ezxhelper.utils.showToast
import org.kitsunepie.qassistant.R
import org.kitsunepie.qassistant.annotations.NormalHookEntry
import org.kitsunepie.qassistant.app.hook.base.BaseHook
import org.kitsunepie.qassistant.app.hook.base.IHookInfo
import java.io.File

@NormalHookEntry
object DefaultBubble : BaseHook(), IHookInfo {

    override fun init() {
    }

    override fun isActivated(): Boolean {
        val dir = File(appContext.filesDir.absolutePath + "/bubble_info")
        return !dir.exists() || !dir.canRead()
    }

    override fun setActivated(value: Boolean) {
        try {
            val dir = File(appContext.filesDir.absolutePath + "/bubble_info")
            val curr = !dir.exists() || !dir.canRead()
            // 开启需要重启QQ，关闭即时生效
            if (value && !curr) Log.toast(InitFields.moduleRes.getString(R.string.reboot_to_effect))

            if (dir.exists()) {
                if (value && !curr) {
                    dir.setWritable(false)
                    dir.setReadable(false)
                    dir.setExecutable(false)
                }
                if (!value && curr) {
                    dir.setWritable(true)
                    dir.setReadable(true)
                    dir.setExecutable(true)
                }
            }
        } catch (e: Exception) {
            Log.e(e)
            appContext.showToast("出错力：$e")
        }
    }

    override val titleRes: Int
        get() = R.string.module_function_setting_purify_chat_default_bubble

    override val descRes: Int
        get() = R.string.module_function_setting_purify_chat_default_bubble_desc

}
