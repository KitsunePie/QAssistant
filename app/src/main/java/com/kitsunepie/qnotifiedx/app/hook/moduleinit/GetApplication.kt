package com.kitsunepie.qnotifiedx.app.hook.moduleinit

import android.app.Application
import com.github.kyuubiran.ezxhelper.init.EzXHelperInit
import com.github.kyuubiran.ezxhelper.init.InitFields.appContext
import com.github.kyuubiran.ezxhelper.utils.Log
import com.github.kyuubiran.ezxhelper.utils.findMethodByCondition
import com.github.kyuubiran.ezxhelper.utils.getFieldBySig
import com.github.kyuubiran.ezxhelper.utils.getStaticNonNullAs
import com.kitsunepie.qnotifiedx.R
import com.kitsunepie.qnotifiedx.app.hook.base.BaseModuleInitHook
import com.kitsunepie.qnotifiedx.app.hook.base.BaseNormalHook
import com.kitsunepie.qnotifiedx.app.util.MMKVInit
import com.kitsunepie.qnotifiedx.app.util.hookAfter
import de.robv.android.xposed.callbacks.XCallback

object GetApplication : BaseModuleInitHook() {
    override val name: String = "获取全局Context"
    override var isEnabled: Boolean = true

    override fun init() {
        findMethodByCondition("com.tencent.mobileqq.startup.step.LoadDex") {
            it.returnType == Boolean::class.java && it.parameterTypes.isEmpty()
        }.also { m ->
            m.hookAfter(this, XCallback.PRIORITY_HIGHEST) {
                //获取Context
                val context =
                    getFieldBySig("Lcom/tencent/common/app/BaseApplicationImpl;->sApplication:Lcom/tencent/common/app/BaseApplicationImpl;")
                        .getStaticNonNullAs<Application>()
                //设置全局Context
                EzXHelperInit.initAppContext(context, true)
                //注入资源
                Log.toast(appContext.resources.getString(R.string.load_successful))
                MMKVInit.init()
                //加载普通Hook
                BaseNormalHook.initHooks()
            }
        }
    }
}