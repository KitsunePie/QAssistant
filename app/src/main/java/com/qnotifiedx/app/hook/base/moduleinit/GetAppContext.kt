package com.qnotifiedx.app.hook.base.moduleinit

import android.app.Application
import com.qnotifiedx.app.util.*

//获取宿主全局ApplicationHook

object GetAppContext {
    var application: Application? = null
        private set

    fun init() {
        findMethodByCondition("com.tencent.mobileqq.startup.step.LoadDex") {
            it.returnType == Boolean::class.java && it.parameterTypes.isEmpty()
        }.also { m ->
            m.hookAfter(100) {
                if (application != null) return@hookAfter
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
            }
        }
    }
}