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

import com.github.kyuubiran.ezxhelper.utils.getFieldByClassOrObject
import com.github.kyuubiran.ezxhelper.utils.loadClass

/**
 * @param clzName 类名
 * @param indexes 类索引
 */
enum class ClassPointer(private val clzName: String, private val indexes: Array<Int>) {
    QbossADImmersionBannerManager(
        "cooperation.vip.qqbanner.QbossADImmersionBannerManager",
        arrayOf(1, 2)
    ),
    ConversationTitleBtnCtrl(
        "com.tencent.mobileqq.activity.ConversationTitleBtnCtrl",
        arrayOf(1, 2, 4, 5, 6)
    ),
    UpgradeController1("com.tencent.mobileqq.upgrade.UpgradeController", arrayOf(1, 2)),
    UpgradeController2("com.tencent.mobileqq.app.upgrade.UpgradeController", arrayOf(1, 2)),
    TextItemBuilder(
        "com.tencent.mobileqq.activity.aio.item.TextItemBuilder",
        arrayOf(3, 6, 7, 8, 10)
    );

    private fun tryLoadOrNull(): Class<*>? {
        runCatching {
            return loadClass(this.clzName)
        }
        this.indexes.forEach { idx ->
            runCatching {
                return loadClass("${this.clzName}\$${idx}").getFieldByClassOrObject("this$0").type
            }
        }
        return null
    }

    val clz: Class<*>? = tryLoadOrNull()
}
