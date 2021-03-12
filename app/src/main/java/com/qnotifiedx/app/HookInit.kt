package com.qnotifiedx.app

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.core.view.size
import com.qnotifiedx.app.util.*
import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.callbacks.XC_LoadPackage

class HookInit : IXposedHookLoadPackage {
    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam) {
        if (lpparam.packageName == "com.tencent.mobileqq") {
            Log.i("已找到QQ 开始加载模块")
            mClzLoader = lpparam.classLoader
            init()
        }
    }
}

private fun init() {
    for (m in getMethods("com.tencent.mobileqq.activity.QQSettingSettingActivity")) {
        if (m.name != "doOnCreate") continue
        m.hookAfter {
            val thisObject = it.thisObject

            val cFormSimpleItem = loadClass("com.tencent.mobileqq.widget.FormSimpleItem")
            val cFormItemRelativeLayout =
                loadClass("com.tencent.mobileqq.widget.FormItemRelativeLayout")

            val vg = thisObject.getObjectOrNull("a", cFormItemRelativeLayout) as ViewGroup?

            val entry = cFormSimpleItem.newInstance(
                arrayOf<Any>(thisObject as Context),
                arrayOf(Context::class.java)
            ) as View

            entry.apply {
                invokeMethod(
                    "setLeftText",
                    arrayOf("QNotifiedX"),
                    arrayOf(CharSequence::class.java)
                )
                setOnClickListener { }
            }

            vg?.addView(entry, vg.size / 2)
            Log.i("入口加载成功")
        }
    }
}