package com.qnotifiedx.app.hook.normal

import android.app.Application
import com.qnotifiedx.app.hook.base.BaseHook
import com.qnotifiedx.app.util.getMethods
import com.qnotifiedx.app.util.getStaticFiledByClass
import com.qnotifiedx.app.util.hookAfter
import com.qnotifiedx.app.util.loadClass

//获取宿主全局ApplicationHook
object GetAppContext : BaseHook() {
    //强制开启
    override var enable: Boolean = true
    var application: Application? = null
        private set

    override fun init() {
        if (application != null) return
        for (m in getMethods("com.tencent.mobileqq.startup.step.LoadDex")) {
            if (m.returnType == Boolean::class.javaPrimitiveType && m.parameterTypes.isEmpty()) {
                m.hookAfter {
                    //加载QQ的基础Application
                    val cBaseApplicationImpl =
                        loadClass("com.tencent.common.app.BaseApplicationImpl")
                    //获取Context
                    val context =
                        cBaseApplicationImpl.getStaticFiledByClass(
                            "sApplication",
                            cBaseApplicationImpl
                        ) as Application
                    application = context
                }
            }
        }
    }
}