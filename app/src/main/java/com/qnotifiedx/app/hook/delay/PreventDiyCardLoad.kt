package com.qnotifiedx.app.hook.delay

import com.qnotifiedx.annotations.DelayHookEntry
import com.qnotifiedx.app.hook.base.BaseDelayHook
import com.qnotifiedx.app.util.*

//阻止DIY名片加载
@DelayHookEntry
object PreventDiyCardLoad : BaseDelayHook() {
    override fun init() {
        findMethodByCondition("com.tencent.mobileqq.profilecard.vas.VasProfileTemplateController") {
            it.name == "a" && it.parameterTypes.size == 2 && it.parameterTypes[1] == Int::class.java && it.isPublic
        }.also { m ->
            m.hookBefore(this) {
                if (!enable) return@hookBefore
                it.result = null
            }
        }
    }
}