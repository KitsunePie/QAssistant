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

import org.kitsunepie.qassistant.R
import org.kitsunepie.qassistant.app.hook.base.BaseHook

object RemoveGroupApp : BaseHook() {
    override fun init() {
    }

    override val titleRes: Int = R.string.module_function_setting_purify_group_remove_group_app

    override val descRes: Int = R.string.module_function_setting_purify_group_remove_group_app_desc
}
