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

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.github.kyuubiran.ezxhelper.utils.findMethod
import com.github.kyuubiran.ezxhelper.utils.hookAfter
import com.github.kyuubiran.ezxhelper.utils.putObject
import org.kitsunepie.qassistant.R
import org.kitsunepie.qassistant.annotations.NormalHookEntry
import org.kitsunepie.qassistant.app.hook.base.BaseHook

@NormalHookEntry
object HideRedDot : BaseHook() {
    override val needReboot: Boolean = true

    private val TRANSPARENT_PNG = byteArrayOf(
        0x89.toByte(), 0x50.toByte(), 0x4E.toByte(), 0x47.toByte(),
        0x0D.toByte(), 0x0A.toByte(), 0x1A.toByte(), 0x0A.toByte(),
        0x00.toByte(), 0x00.toByte(), 0x00.toByte(), 0x0D.toByte(),
        0x49.toByte(), 0x48.toByte(), 0x44.toByte(), 0x52.toByte(),
        0x00.toByte(), 0x00.toByte(), 0x00.toByte(), 0x01.toByte(),
        0x00.toByte(), 0x00.toByte(), 0x00.toByte(), 0x01.toByte(),
        0x08.toByte(), 0x06.toByte(), 0x00.toByte(), 0x00.toByte(),
        0x00.toByte(), 0x1F.toByte(), 0x15.toByte(), 0xC4.toByte(),
        0x89.toByte(), 0x00.toByte(), 0x00.toByte(), 0x00.toByte(),
        0x0B.toByte(), 0x49.toByte(), 0x44.toByte(), 0x41.toByte(),
        0x54.toByte(), 0x08.toByte(), 0xD7.toByte(), 0x63.toByte(),
        0x60.toByte(), 0x00.toByte(), 0x02.toByte(), 0x00.toByte(),
        0x00.toByte(), 0x05.toByte(), 0x00.toByte(), 0x01.toByte(),
        0xE2.toByte(), 0x26.toByte(), 0x05.toByte(), 0x9B.toByte(),
        0x00.toByte(), 0x00.toByte(), 0x00.toByte(), 0x00.toByte(),
        0x49.toByte(), 0x45.toByte(), 0x4E.toByte(), 0x44.toByte(),
        0xAE.toByte(), 0x42.toByte(), 0x60.toByte(), 0x82.toByte()
    )

    override fun init() {
        findMethod("com.tencent.theme.ResourcesFactory") {
            (name == "createImageFromResourceStream" || name == "a") && parameterTypes.size == 7
        }.hookAfter { param ->
            if (!param.args[3].toString().contains("skin_tips_dot")) return@hookAfter
            param.result.putObject(
                "a",
                BitmapFactory.decodeByteArray(TRANSPARENT_PNG, 0, TRANSPARENT_PNG.size),
                Bitmap::class.java
            )
        }
    }

    override val titleRes: Int = R.string.module_function_setting_purify_group_other_hide_red_dot

    override val descRes: Int =
        R.string.module_function_setting_purify_group_other_hide_red_dot_desc
}
