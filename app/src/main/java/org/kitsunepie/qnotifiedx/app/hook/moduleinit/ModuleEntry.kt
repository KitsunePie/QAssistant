package org.kitsunepie.qnotifiedx.app.hook.moduleinit

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.core.view.size
import com.github.kyuubiran.ezxhelper.init.InitFields.moduleRes
import com.github.kyuubiran.ezxhelper.utils.*
import de.robv.android.xposed.callbacks.XCallback
import org.kitsunepie.qnotifiedx.R
import org.kitsunepie.qnotifiedx.app.hook.base.BaseModuleInitHook
import org.kitsunepie.qnotifiedx.app.util.hookAfter

object ModuleEntry : BaseModuleInitHook() {
    override val name: String = "模块入口"
    override var isEnabled: Boolean = true

    override fun init() {
        getMethodBySig("Lcom/tencent/mobileqq/activity/QQSettingSettingActivity;->doOnCreate(Landroid/os/Bundle;)Z").also { m ->
            m.hookAfter(this, XCallback.PRIORITY_HIGHEST) { param ->
                val cFormSimpleItem = try {
                    loadClass("com.tencent.mobileqq.widget.FormSimpleItem")
                } catch (e: Exception) {
                    loadClass("com.tencent.mobileqq.widget.FormCommonSingleLineItem")
                }
                //获取ViewGroup
                val vg: ViewGroup = try {
                    param.thisObject.getObjectAs("a", cFormSimpleItem)
                } catch (e: Exception) {
                    param.thisObject.getObjectOrNullByType(cFormSimpleItem) as View
                }.parent as ViewGroup
                //创建入口
                val entry = cFormSimpleItem.newInstanceAs<View>(
                    arrayOf(param.thisObject),
                    arrayOf(Context::class.java)
                )!!.also {
                    it.invokeMethod(
                        "setLeftText",
                        arrayOf("QNotifiedX"),
                        arrayOf(CharSequence::class.java)
                    )
                    it.invokeMethod(
                        "setRightText",
                        arrayOf(org.kitsunepie.qnotifiedx.BuildConfig.VERSION_NAME),
                        arrayOf(CharSequence::class.java)
                    )
                }
                entry.setOnClickListener {
                    Log.toast(moduleRes.getString(R.string.nothing_here))
                }
                //添加入口
                vg.addView(entry, (vg.size / 2) - 4)
            }
        }
    }
}