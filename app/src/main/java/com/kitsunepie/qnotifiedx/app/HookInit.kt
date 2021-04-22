package com.kitsunepie.qnotifiedx.app

import com.github.kyuubiran.ezxhelper.init.EzXHelperInit
import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.callbacks.XC_LoadPackage

class HookInit : IXposedHookLoadPackage {
    companion object {
        lateinit var processName: String
            private set
        lateinit var packageName: String
            private set
    }

    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam) {
        if (lpparam.packageName == "com.tencent.mobileqq") {
            EzXHelperInit.initHandleLoadPackage(lpparam)
            EzXHelperInit.setLogTag("QNotifiedX")
            packageName = lpparam.packageName
            processName = lpparam.processName
            HookLoader.init
        }
    }
}
