package com.qnotifiedx.app.hook.normal.simplify

import com.github.kyuubiran.ezxhelper.utils.findMethodByCondition
import com.github.kyuubiran.ezxhelper.utils.isPublic
import com.qnotifiedx.annotations.NormalHookEntry
import com.qnotifiedx.app.hook.base.BaseNormalHook
import com.qnotifiedx.app.util.hookBefore

@NormalHookEntry
object PreventDiyCardLoad : BaseNormalHook() {
    override val name: String = "阻止DIY名片加载"

    override fun init() {
        findMethodByCondition("com.tencent.mobileqq.profilecard.vas.VasProfileTemplateController") {
            it.name == "a" && it.parameterTypes.size == 2 && it.parameterTypes[1] == Int::class.java && it.isPublic
        }.also { m ->
            m.hookBefore(this) {
                it.result = null
            }
        }
    }
}