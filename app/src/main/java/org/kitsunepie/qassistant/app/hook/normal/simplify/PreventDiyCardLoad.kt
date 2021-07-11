package org.kitsunepie.qassistant.app.hook.normal.simplify

import com.github.kyuubiran.ezxhelper.utils.getMethodBySig
import org.kitsunepie.qassistant.annotations.NormalHookEntry
import org.kitsunepie.qassistant.app.hook.base.BaseSwitchHook
import org.kitsunepie.qassistant.app.util.hookBefore

@NormalHookEntry
object PreventDiyCardLoad : BaseSwitchHook() {
    override var title: String = "阻止DIY名片加载"
    override var summary: String? = "有效防止闪退名片、Zip炸弹的最佳手段"

    override fun init() {
        getMethodBySig("Lcom/tencent/mobileqq/profilecard/vas/VasProfileTemplateController;->a(Lcom/tencent/mobileqq/data/Card;I)V")
            .hookBefore(this) { param ->
                param.result = null
            }
    }
}