package com.qnotifiedx.app.hook.moduleinit

import android.app.Application
import com.github.kyuubiran.ezxhelper.init.EzXHelperInit
import com.github.kyuubiran.ezxhelper.utils.Log
import com.github.kyuubiran.ezxhelper.utils.findMethodByCondition
import com.github.kyuubiran.ezxhelper.utils.getStaticObjectOrNull
import com.github.kyuubiran.ezxhelper.utils.loadClass
import com.qnotifiedx.app.hook.base.BaseModuleInit
import com.qnotifiedx.app.hook.base.BaseNormalHook
import com.qnotifiedx.app.util.MMKVInit
import com.qnotifiedx.app.util.hookAfter
import com.qnotifiedx.core.resinjection.ResInjector

object GetApplication : BaseModuleInit() {
    override val name: String = "获取Context"
    override var enable: Boolean = true

    override fun init() {
        findMethodByCondition("com.tencent.mobileqq.startup.step.LoadDex") {
            it.returnType == Boolean::class.java && it.parameterTypes.isEmpty()
        }.also { m ->
            m.hookAfter(this, 100) {
                //加载QQ的基础Application
                val cBaseApplicationImpl =
                    loadClass("com.tencent.common.app.BaseApplicationImpl")
                //获取Context
                val context =
                    cBaseApplicationImpl.getStaticObjectOrNull(
                        "sApplication",
                        cBaseApplicationImpl
                    ) as Application
                EzXHelperInit.initAppContext(context)
                //资源注入部分
                ResInjector.initSubActivity()
                ResInjector.injectRes()
                MMKVInit.init()
                //延迟Hook部分
                BaseNormalHook.initHooks()
                inited = true
            }
        }
    }
}