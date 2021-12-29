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

package org.kitsunepie.qassistant.app.hook.normal.function

import android.widget.EditText
import com.github.kyuubiran.ezxhelper.utils.*
import org.kitsunepie.qassistant.R
import org.kitsunepie.qassistant.annotations.NormalHookEntry
import org.kitsunepie.qassistant.app.hook.base.BaseHook
import org.kitsunepie.qassistant.app.hook.base.IHookInfo

@NormalHookEntry
object UnlockUniqueTitleLength : BaseHook(), IHookInfo {
    override fun init() {
        //EditText控制
        getMethodByDesc("Lcom/tencent/biz/troop/EditUniqueTitleActivity;->doOnCreate(Landroid/os/Bundle;)Z")
            .hookAfter { param ->
                val et = param.thisObject.getObjectAs<EditText>("a", EditText::class.java)
                et.filters = emptyArray()
                param.thisObject.putObject("a", Int.MAX_VALUE, Int::class.java)
            }
        //完成按钮
        getMethodByDesc("Lcom/tencent/biz/troop/EditUniqueTitleActivity;->a(Lcom/tencent/biz/troop/EditUniqueTitleActivity;Z)V")
            .hookBefore { param ->
                param.args[1] = true
            }
    }

    override val titleRes: Int
        get() = R.string.module_function_setting_assistant_group_unlock_unique_title_length

    override val descRes: Int
        get() = R.string.module_function_setting_assistant_group_unlock_unique_title_length_desc
}
