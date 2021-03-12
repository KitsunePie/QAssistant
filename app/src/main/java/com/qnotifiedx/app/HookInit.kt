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
            //加载QQ的设置物件类
            val cFormSimpleItem = loadClass("com.tencent.mobileqq.widget.FormSimpleItem")
            //获取所在的ViewGroup
            val vg = (thisObject.getObjectOrNull("a", cFormSimpleItem) as View).parent as ViewGroup
            //创建入口View
            val entry = cFormSimpleItem.newInstance(
                arrayOf<Any>(thisObject as Context),
                arrayOf(Context::class.java)
            ) as View
            //设置入口属性
            entry.apply {
                invokeMethod(
                    "setLeftText",
                    arrayOf("QNotifiedX"),
                    arrayOf(CharSequence::class.java)
                )
                invokeMethod(
                    "setRightText",
                    arrayOf(BuildConfig.VERSION_NAME),
                    arrayOf(CharSequence::class.java)
                )
                setOnClickListener { }
            }
            //添加入口
            vg.addView(entry, (vg.size / 2) - 4)
        }
    }
}