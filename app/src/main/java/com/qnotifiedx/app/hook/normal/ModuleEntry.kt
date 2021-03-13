package com.qnotifiedx.app.hook.normal

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.core.view.size
import com.qnotifiedx.app.BuildConfig
import com.qnotifiedx.app.hook.base.BaseHook
import com.qnotifiedx.app.util.*

object ModuleEntry : BaseHook() {
    override var enable: Boolean = true

    override fun init() {
        for (m in getMethods("com.tencent.mobileqq.activity.QQSettingSettingActivity")) {
            if (m.name != "doOnCreate") continue
            m.hookAfter {
                val thisObject = it.thisObject
                //加载QQ的设置物件类
                val cFormSimpleItem = loadClass("com.tencent.mobileqq.widget.FormSimpleItem")
                //获取所在的ViewGroup
                val vg =
                    (thisObject.getObjectOrNull("a", cFormSimpleItem) as View).parent as ViewGroup
                //创建入口View
                val entry = cFormSimpleItem.newInstance(
                    arrayOf(thisObject as Context),
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
                    setOnClickListener {
                        appContext?.showToast("还没有准备好哦~")
                    }
                }
                //添加入口
                vg.addView(entry, (vg.size / 2) - 4)
            }
        }
    }
}