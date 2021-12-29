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

package org.kitsunepie.qassistant.app.hook.moduleinit

import android.content.Context
import android.view.View
import android.view.ViewGroup
import com.github.kyuubiran.ezxhelper.init.InitFields.moduleRes
import com.github.kyuubiran.ezxhelper.utils.*
import org.kitsunepie.qassistant.R
import org.kitsunepie.qassistant.app.hook.base.BaseHook

object ModuleEntry : BaseHook() {
    override fun isActivated(): Boolean {
        return true
    }

    override val titleRes: Int
        get() = R.string.empty

    override fun init() {
        getMethodByDesc("Lcom/tencent/mobileqq/activity/QQSettingSettingActivity;->doOnCreate(Landroid/os/Bundle;)Z").also { m ->
            m.hookAfter { param ->
                val cFormSimpleItem = try {
                    loadClass("com.tencent.mobileqq.widget.FormSimpleItem")
                } catch (e: Exception) {
                    loadClass("com.tencent.mobileqq.widget.FormCommonSingleLineItem")
                }
                //获取ViewGroup
                val vg: ViewGroup = try {
                    param.thisObject.getObjectAs("a", cFormSimpleItem)
                } catch (e: Exception) {
                    param.thisObject.getObjectOrNullByType(cFormSimpleItem) as View
                }.parent as ViewGroup
                //创建入口
                val entry = cFormSimpleItem.newInstanceAs<View>(
                    arrayOf(param.thisObject),
                    arrayOf(Context::class.java)
                )!!.also {
                    it.invokeMethod(
                        "setLeftText",
                        arrayOf(moduleRes.getString(R.string.app_name)),
                        arrayOf(CharSequence::class.java)
                    )
                    it.invokeMethod(
                        "setRightText",
                        arrayOf(org.kitsunepie.qassistant.BuildConfig.VERSION_NAME),
                        arrayOf(CharSequence::class.java)
                    )
                }
                /*
                entry.setOnClickListener {
                    val intent = Intent(param.thisObject as Activity, ModuleActivity::class.java)
                    (param.thisObject as Activity).startActivity(intent)
                }*/
                //添加入口
                vg.addView(entry, (vg.childCount / 2) - 4)
            }
        }
    }
}
