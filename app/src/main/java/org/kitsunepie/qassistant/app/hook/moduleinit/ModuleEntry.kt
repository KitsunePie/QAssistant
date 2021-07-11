package org.kitsunepie.qassistant.app.hook.moduleinit

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.View
import android.view.ViewGroup
import androidx.core.view.size
import com.github.kyuubiran.ezxhelper.init.InitFields.moduleRes
import com.github.kyuubiran.ezxhelper.utils.*
import de.robv.android.xposed.callbacks.XCallback
import org.kitsunepie.qassistant.R
import org.kitsunepie.qassistant.app.hook.base.BaseHook
import org.kitsunepie.qassistant.app.ui.module.activity.ModuleActivity
import org.kitsunepie.qassistant.app.util.hookAfter

object ModuleEntry : BaseHook {
    override fun isActivated(): Boolean {
        return true
    }

    override var isInit: Boolean = false

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
                        arrayOf(moduleRes.getString(R.string.app_name)),
                        arrayOf(CharSequence::class.java)
                    )
                    it.invokeMethod(
                        "setRightText",
                        arrayOf(org.kitsunepie.qassistant.BuildConfig.VERSION_NAME),
                        arrayOf(CharSequence::class.java)
                    )
                }
                entry.setOnClickListener {
                    val intent = Intent(param.thisObject as Activity, ModuleActivity::class.java)
                    (param.thisObject as Activity).startActivity(intent)
                }
                //添加入口
                vg.addView(entry, (vg.size / 2) - 4)
            }
        }
    }
}