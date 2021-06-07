package com.kitsunepie.qnotifiedx.app.hook.moduleinit

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.core.view.size
import com.github.kyuubiran.ezxhelper.utils.*
import com.kitsunepie.qnotifiedx.BuildConfig
import com.kitsunepie.qnotifiedx.app.hook.base.BaseModuleInit
import com.kitsunepie.qnotifiedx.app.util.hookAfter
import de.robv.android.xposed.callbacks.XCallback

object ModuleEntry : BaseModuleInit() {
    override val name: String = "模块入口"
    override var isEnabled: Boolean = true

    override fun init() {
        findMethodByCondition("com.tencent.mobileqq.activity.QQSettingSettingActivity") {
            it.name == "doOnCreate"
        }.also { m ->
            m.hookAfter(this, XCallback.PRIORITY_HIGHEST) {
                val thisObject = it.thisObject
                //加载QQ的设置物件类
                val cFormSimpleItem = loadClass("com.tencent.mobileqq.widget.FormSimpleItem")
                //获取所在的ViewGroup
                val vg =
                    thisObject.getObjectAs<View>(
                        "a",
                        cFormSimpleItem
                    ).parent as ViewGroup
                //创建入口View
                val entry = cFormSimpleItem.newInstanceAs<View>(
                    arrayOf(thisObject as Context),
                    arrayOf(Context::class.java)
                )!!
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
                        context?.showToast("还没有准备好哦~")
                    }
                    setOnLongClickListener {
                        true
                    }
                }
                //添加入口
                vg.addView(entry, (vg.size / 2) - 4)
            }
        }
    }
}