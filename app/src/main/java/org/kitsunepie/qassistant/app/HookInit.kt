package org.kitsunepie.qassistant.app

import com.github.kyuubiran.ezxhelper.init.EzXHelperInit
import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.IXposedHookZygoteInit
import de.robv.android.xposed.callbacks.XC_LoadPackage

class HookInit : IXposedHookLoadPackage, IXposedHookZygoteInit {
    companion object {
        lateinit var processName: String
            private set
        lateinit var packageName: String
            private set
    }

    override fun initZygote(startupParam: IXposedHookZygoteInit.StartupParam) {
        EzXHelperInit.initZygote(startupParam)
    }

    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam) {
        if (lpparam.packageName == "com.tencent.mobileqq") {
            EzXHelperInit.initHandleLoadPackage(lpparam)
            EzXHelperInit.setLogTag("QAssistant")
            EzXHelperInit.setToastTag("QAssistant")
            packageName = lpparam.packageName
            processName = lpparam.processName
            HookLoader.init
        }
    }
}
