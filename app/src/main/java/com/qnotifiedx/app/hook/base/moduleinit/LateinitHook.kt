package com.qnotifiedx.app.hook.base.moduleinit

import android.app.Application
import com.qnotifiedx.app.hook.base.BaseDelayHook
import com.qnotifiedx.app.util.*
import com.qnotifiedx.core.resinjection.ResInjector

//获取宿主Application && 延迟加载
object LateinitHook {
    var application: Application? = null
        private set
    private var inited = false

    fun init() {
        findMethodByCondition("com.tencent.mobileqq.startup.step.LoadDex") {
            it.returnType == Boolean::class.java && it.parameterTypes.isEmpty()
        }.also { m ->
            m.hookAfter(100) {
                if (inited) return@hookAfter
                //加载QQ的基础Application
                val cBaseApplicationImpl =
                    loadClass("com.tencent.common.app.BaseApplicationImpl")
                //获取Context
                val context =
                    cBaseApplicationImpl.getStaticObjectOrNull(
                        "sApplication",
                        cBaseApplicationImpl
                    ) as Application
                application = context
                //资源注入部分
                ResInjector.initSubActivity()
                ResInjector.injectRes(appContext!!.resources)
                //延迟Hook部分
                BaseDelayHook.initHooks()
                inited = true
            }
        }
    }
}