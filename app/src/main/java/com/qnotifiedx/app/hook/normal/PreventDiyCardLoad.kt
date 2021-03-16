package com.qnotifiedx.app.hook.normal

import com.qnotifiedx.annotations.NormalHookEntry
import com.qnotifiedx.app.hook.base.BaseNormalHook
import com.qnotifiedx.app.util.*
import com.qnotifiedx.core.processctrl.Process

@NormalHookEntry
object PreventDiyCardLoad : BaseNormalHook() {
    override val name: String = "阻止DIY名片加载"

    override fun init() {
        findMethodByCondition("com.tencent.mobileqq.profilecard.vas.VasProfileTemplateController") {
            it.name == "a" && it.parameterTypes.size == 2 && it.parameterTypes[1] == Int::class.java && it.isPublic
        }.also { m ->
            m.hookBefore {
                if (!enable) return@hookBefore
                it.result = null
            }
        }
    }
}