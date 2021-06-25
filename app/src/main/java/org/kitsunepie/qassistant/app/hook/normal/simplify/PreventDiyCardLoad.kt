package org.kitsunepie.qassistant.app.hook.normal.simplify

import com.github.kyuubiran.ezxhelper.utils.getMethodBySig
import org.kitsunepie.qassistant.app.hook.base.BaseNormalHook
import org.kitsunepie.qassistant.app.util.hookBefore

@org.kitsunepie.qassistant.annotations.NormalHookEntry
object PreventDiyCardLoad : BaseNormalHook() {
    override val name: String = "阻止DIY名片加载"
    override val desc: String = "有效防止闪退名片、Zip炸弹的最佳手段"

    override fun init() {
        getMethodBySig("Lcom/tencent/mobileqq/profilecard/vas/VasProfileTemplateController;->a(Lcom/tencent/mobileqq/data/Card;I)V").also { m ->
            m.hookBefore(this) { param ->
                param.result = null
            }
        }
    }
}