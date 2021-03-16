package com.qnotifiedx.app.hook.normal

import android.view.View
import com.qnotifiedx.annotations.NormalHookEntry
import com.qnotifiedx.app.hook.base.BaseNormalHook
import com.qnotifiedx.app.util.*
import com.qnotifiedx.core.processctrl.Process

@NormalHookEntry
object PreventQBossAdLoad : BaseNormalHook() {
    override val name: String = "阻止主界面横幅广告加载"

    override fun init() {
        findMethodByCondition(ClassPointer.QbossADImmersionBannerManager.clazz!!) {
            it.returnType == View::class.java && it.parameterTypes.isEmpty() && it.isStatic
        }.also { m ->
            m.hookBefore {
                if (!enable) return@hookBefore
                it.result = null
            }
        }
    }
}