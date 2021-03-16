package com.qnotifiedx.app

import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.callbacks.XC_LoadPackage

class HookInit : IXposedHookLoadPackage {
    companion object {
        lateinit var clzLoader: ClassLoader
            private set
        lateinit var processName: String
            private set
        lateinit var packageName: String
            private set
    }

    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam) {
        if (lpparam.packageName == "com.tencent.mobileqq") {
            packageName = lpparam.packageName
            clzLoader = lpparam.classLoader
            processName = lpparam.processName
            HookLoader.init
        }
    }
}
