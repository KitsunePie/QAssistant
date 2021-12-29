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

import android.content.pm.PackageInfo
import android.os.Build
import com.github.kyuubiran.ezxhelper.init.InitFields.appContext
import org.kitsunepie.qassistant.app.HookInit


/**
 * 包含了模块版本号信息以及宿主版本号信息
 * 在执行获取宿主app的hook之前调用会返回null(除了HOST_PACKAGE_NAME)
 */
object Info {
    const val MODULE_PACKAGE_NAME = org.kitsunepie.qassistant.BuildConfig.APPLICATION_ID
    const val MODULE_VERSION_CODE = org.kitsunepie.qassistant.BuildConfig.VERSION_CODE
    const val MODULE_VERSION_NAME = org.kitsunepie.qassistant.BuildConfig.VERSION_NAME

    val HOST_PACKAGE_NAME by lazy {
        HookInit.packageName
    }

    @Suppress("DEPRECATION")
    val HOST_VERSION_CODE =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) getHostPackageInfo()?.longVersionCode else getHostPackageInfo()?.versionCode?.toLong()
    val HOST_VERSION_NAME: String? = getHostPackageInfo()?.versionName

    private fun getHostPackageInfo(): PackageInfo? {
        return HOST_PACKAGE_NAME.let { appContext.packageManager?.getPackageInfo(it, 0) }
    }
}
