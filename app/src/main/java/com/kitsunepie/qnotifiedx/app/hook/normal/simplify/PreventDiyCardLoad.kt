package com.kitsunepie.qnotifiedx.app.hook.normal.simplify

import com.github.kyuubiran.ezxhelper.utils.findMethodByCondition
import com.github.kyuubiran.ezxhelper.utils.isPublic
import com.kitsunepie.qnotifiedx.annotations.NormalHookEntry
import com.kitsunepie.qnotifiedx.app.hook.base.BaseNormalHook
import com.kitsunepie.qnotifiedx.app.util.hookBefore

@NormalHookEntry
object PreventDiyCardLoad : BaseNormalHook() {
    override val name: String = "阻止DIY名片加载"
    override val desc: String = "有效防止闪退名片、Zip炸弹的最佳手段"

    override fun init() {
        findMethodByCondition("com.tencent.mobileqq.profilecard.vas.VasProfileTemplateController") {
            it.name == "a" && it.parameterTypes.size == 2 && it.parameterTypes[1] == Int::class.java && it.isPublic
        }.also { m ->
            m.hookBefore(this) { param ->
                param.result = null
            }
        }
    }
}