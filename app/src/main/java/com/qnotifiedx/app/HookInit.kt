package com.qnotifiedx.app

import com.qnotifiedx.app.hook.base.BaseDelayHook
import com.qnotifiedx.app.hook.base.BaseNormalHook
import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.callbacks.XC_LoadPackage

class HookInit : IXposedHookLoadPackage {
    companion object {
        lateinit var clzLoader: ClassLoader
            private set
    }

    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam) {
        if (lpparam.packageName == "com.tencent.mobileqq") {
            clzLoader = lpparam.classLoader
            init()
        }
    }

    private fun init() {
        BaseNormalHook.initHooks()
        BaseDelayHook.initHooks()
    }
}
