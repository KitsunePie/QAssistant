package com.qnotifiedx.app.hook.moduleinit

import android.content.Context
import android.content.Intent
import android.view.View
import android.view.ViewGroup
import androidx.core.view.size
import com.github.kyuubiran.ezxhelper.utils.*
import com.qnotifiedx.app.BuildConfig
import com.qnotifiedx.app.hook.base.BaseModuleInit
import com.qnotifiedx.app.ui.module.activity.MainActivity
import com.qnotifiedx.app.util.hookAfter

object ModuleEntry : BaseModuleInit() {
    override val name: String = "模块入口"
    override var enable: Boolean = true

    override fun init() {
        findMethodByCondition("com.tencent.mobileqq.activity.QQSettingSettingActivity") {
            it.name == "doOnCreate"
        }.also { m ->
            m.hookAfter(this, 100) {
                val thisObject = it.thisObject
                //加载QQ的设置物件类
                val cFormSimpleItem = loadClass("com.tencent.mobileqq.widget.FormSimpleItem")
                //获取所在的ViewGroup
                val vg =
                    (thisObject.getObjectOrNull(
                        "a",
                        cFormSimpleItem
                    ) as View).parent as ViewGroup
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
                        context?.showToast("还没有准备好哦~")
                    }
                    setOnLongClickListener {
                        context?.showToast("好吧 这是你要看的")
                        try {
                            val intent = Intent(context, MainActivity::class.java)
                            context.startActivity(intent)
                            true
                        } catch (e: Exception) {
                            Log.e(e)
                            throw e
                        }
                    }
                }
                //添加入口
                vg.addView(entry, (vg.size / 2) - 4)
            }
        }
    }
}