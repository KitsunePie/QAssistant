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
 * 用于寻找内部类/匿名函数持有的外部类的静态对象(this$0)所指向的类型
 */
enum class ClassPointer {
    QbossADImmersionBannerManager,
    ConversationTitleBtnCtrl,
    UpgradeController1,
    UpgradeController2,
    TextItemBuilder
}

//此处声明包含this$0的类的类名
val ClassPointer.clzName: String
    get() {
        return when (this) {
            ClassPointer.QbossADImmersionBannerManager -> "cooperation.vip.qqbanner.QbossADImmersionBannerManager"
            ClassPointer.ConversationTitleBtnCtrl -> "com.tencent.mobileqq.activity.ConversationTitleBtnCtrl"
            ClassPointer.UpgradeController1 -> "com.tencent.mobileqq.upgrade.UpgradeController"
            ClassPointer.UpgradeController2 -> "com.tencent.mobileqq.app.upgrade.UpgradeController"
            ClassPointer.TextItemBuilder -> "com.tencent.mobileqq.activity.aio.item.TextItemBuilder"
        }
    }

//此处标明类后面的数字
val ClassPointer.clzIndex: Array<Int>
    get() {
        return when (this) {
            ClassPointer.QbossADImmersionBannerManager -> arrayOf(1, 2)
            ClassPointer.ConversationTitleBtnCtrl -> arrayOf(1, 2, 4, 5, 6)
            ClassPointer.UpgradeController1 -> arrayOf(1, 2)
            ClassPointer.UpgradeController2 -> arrayOf(1, 2)
            ClassPointer.TextItemBuilder -> arrayOf(3, 6, 7, 8, 10)
        }
    }

//使用.clazz来获取类 如果获取失败则返回null
val ClassPointer.clazz: Class<*>?
    get() {
        runCatching {
            return loadClass(this.clzName)
        }
        this.clzIndex.forEach { idx ->
            runCatching {
                return loadClass("${this.clzName}\$${idx}").getFieldByClassOrObject("this$0").type
            }
        }
        return null
    }
