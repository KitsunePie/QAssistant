package com.kitsunepie.qnotifiedx.app.hook.moduleinit

import android.app.Application
import com.github.kyuubiran.ezxhelper.init.EzXHelperInit
import com.github.kyuubiran.ezxhelper.utils.findMethodByCondition
import com.github.kyuubiran.ezxhelper.utils.getStaticObjectAs
import com.github.kyuubiran.ezxhelper.utils.loadClass
import com.kitsunepie.qnotifiedx.app.hook.base.BaseModuleInitHook
import com.kitsunepie.qnotifiedx.app.hook.base.BaseNormalHook
import com.kitsunepie.qnotifiedx.app.util.MMKVInit
import com.kitsunepie.qnotifiedx.app.util.hookAfter
import de.robv.android.xposed.callbacks.XCallback

object GetApplication : BaseModuleInitHook() {
    override val name: String = "获取Context"
    override var isEnabled: Boolean = true

    override fun init() {
        findMethodByCondition("com.tencent.mobileqq.startup.step.LoadDex") {
            it.returnType == Boolean::class.java && it.parameterTypes.isEmpty()
        }.also { m ->
            m.hookAfter(this, XCallback.PRIORITY_HIGHEST) {
                //加载QQ的基础Application
                val cBaseApplicationImpl =
                    loadClass("com.tencent.common.app.BaseApplicationImpl")
                //获取Context
                val context =
                    cBaseApplicationImpl.getStaticObjectAs<Application>(
                        "sApplication",
                        cBaseApplicationImpl
                    )
                //初始化全局Context
                EzXHelperInit.initAppContext(context)
                MMKVInit.init()
                //加载普通Hook
                BaseNormalHook.initHooks()
                isInited = true
            }
        }
    }
}