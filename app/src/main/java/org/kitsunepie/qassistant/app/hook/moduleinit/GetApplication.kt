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

import android.app.Application
import com.github.kyuubiran.ezxhelper.init.EzXHelperInit
import com.github.kyuubiran.ezxhelper.init.InitFields.appContext
import com.github.kyuubiran.ezxhelper.utils.*
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.callbacks.XCallback
import org.kitsunepie.qassistant.BuildConfig
import org.kitsunepie.qassistant.R
import org.kitsunepie.qassistant.app.HookInit
import org.kitsunepie.qassistant.app.hook.base.BaseHook
import org.kitsunepie.qassistant.app.hook.base.HookInitializer
import org.kitsunepie.qassistant.core.config.Config
import org.kitsunepie.qassistant.core.config.ModuleConfig

object GetApplication : BaseHook() {
    override var isActivated: Boolean
        get() = true
        set(_) {}

    override val titleRes: Int
        get() = R.string.empty

    private var unhook: XC_MethodHook.Unhook? = null

    override fun init() {
        getMethodByDesc("Lcom/tencent/mobileqq/startup/step/LoadDex;->doStep()Z").hookAfter(
            XCallback.PRIORITY_HIGHEST
        ) {
            unhook?.unhook()
            //获取Context
            val context =
                getFieldByDesc("Lcom/tencent/common/app/BaseApplicationImpl;->sApplication:Lcom/tencent/common/app/BaseApplicationImpl;")
                    .getStaticNonNullAs<Application>()
            //设置全局Context
            EzXHelperInit.initAppContext(context, true)
            EzXHelperInit.initActivityProxyManager(
                BuildConfig.APPLICATION_ID,
                "com.tencent.mobileqq.activity.photo.CameraPreviewActivity",
                HookInit::class.java.classLoader!!
            )
            EzXHelperInit.initSubActivity()
            if (!Config.sModulePref.getBoolean(ModuleConfig.M_STARTUP_TOAST, false)) {
                Log.toast(appContext.resources.getString(R.string.load_successful))
            }
            //加载普通Hook
            HookInitializer.initNormalHooks()
        }
    }
}
