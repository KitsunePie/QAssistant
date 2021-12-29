package org.kitsunepie.qassistant.app.hook.normal.simplify

import com.github.kyuubiran.ezxhelper.utils.getMethodByDesc
import com.github.kyuubiran.ezxhelper.utils.hookBefore
import org.kitsunepie.qassistant.annotations.NormalHookEntry
import org.kitsunepie.qassistant.app.hook.base.BaseHook
import org.kitsunepie.qassistant.app.hook.base.IHookInfo

@NormalHookEntry
object PreventDiyCardLoad : BaseHook(), IHookInfo {
    override fun init() {
        getMethodByDesc("Lcom/tencent/mobileqq/profilecard/vas/VasProfileTemplateController;->a(Lcom/tencent/mobileqq/data/Card;I)V")
            .hookBefore { param ->
                param.result = null
            }
    }

    override val title: String
        get() = "阻止DIY名片加载"

    override val summary: String
        get() = "防止有人在DIY名片里下毒的最好手段"
}
