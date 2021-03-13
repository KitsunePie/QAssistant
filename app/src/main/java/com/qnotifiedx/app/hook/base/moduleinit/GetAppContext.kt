package com.qnotifiedx.app.hook.base.moduleinit

import android.app.Application
import com.qnotifiedx.app.util.*

//获取宿主全局ApplicationHook

object GetAppContext {
    var application: Application? = null
        private set

    fun init() {
        for (m in getMethods("com.tencent.mobileqq.startup.step.LoadDex")) {
            if (m.returnType == Boolean::class.javaPrimitiveType && m.parameterTypes.isEmpty()) {
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
}