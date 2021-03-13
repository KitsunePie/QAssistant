package com.qnotifiedx.app.hook.normal

import android.app.Application
import com.qnotifiedx.app.hook.base.BaseHook
import com.qnotifiedx.app.util.getMethods
import com.qnotifiedx.app.util.getStaticFiledByClass
import com.qnotifiedx.app.util.hookAfter
import com.qnotifiedx.app.util.loadClass

object GetAppContext : BaseHook() {
    override var enable: Boolean = true
    var application: Application? = null
        private set

    override fun init() {
        if (application != null) return
        for (m in getMethods("com.tencent.mobileqq.startup.step.LoadDex")) {
            if (m.returnType == Boolean::class.javaPrimitiveType && m.parameterTypes.isEmpty()) {
                m.hookAfter {
                    val cBaseApplicationImpl =
                        loadClass("com.tencent.common.app.BaseApplicationImpl")
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