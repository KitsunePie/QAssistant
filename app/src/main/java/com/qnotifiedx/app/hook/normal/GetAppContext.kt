package com.qnotifiedx.app.hook.normal

import android.app.Application
import com.qnotifiedx.annotations.NormalHookEntry
import com.qnotifiedx.app.hook.base.BaseNormalHook
import com.qnotifiedx.app.util.*

//获取宿主全局ApplicationHook
@NormalHookEntry
object GetAppContext : BaseNormalHook() {
    //强制开启
    override var enable: Boolean = true
    var application: Application? = null
        private set

    override fun init() {
        for (m in getMethods("com.tencent.mobileqq.startup.step.LoadDex")) {
            if (m.returnType == Boolean::class.javaPrimitiveType && m.parameterTypes.isEmpty()) {
                m.hookAfter {
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