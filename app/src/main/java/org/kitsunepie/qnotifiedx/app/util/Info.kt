package org.kitsunepie.qnotifiedx.app.util

import android.content.pm.PackageInfo
import android.os.Build
import com.github.kyuubiran.ezxhelper.init.InitFields.appContext
import org.kitsunepie.qnotifiedx.app.HookInit


/**
 * 包含了模块版本号信息以及宿主版本号信息
 * 在执行获取宿主app的hook之前调用会返回null(除了HOST_PACKAGE_NAME)
 */
object Info {
    const val MODULE_PACKAGE_NAME = org.kitsunepie.qnotifiedx.BuildConfig.APPLICATION_ID
    const val MODULE_VERSION_CODE = org.kitsunepie.qnotifiedx.BuildConfig.VERSION_CODE
    const val MODULE_VERSION_NAME = org.kitsunepie.qnotifiedx.BuildConfig.VERSION_NAME

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