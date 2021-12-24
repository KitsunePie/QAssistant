package org.kitsunepie.qassistant.app.hook.normal.simplify

import com.github.kyuubiran.ezxhelper.utils.getMethodByDesc
import com.github.kyuubiran.ezxhelper.utils.hookBefore
import org.kitsunepie.qassistant.annotations.NormalHookEntry
import org.kitsunepie.qassistant.app.hook.base.BaseHook

@NormalHookEntry
object PreventDiyCardLoad : BaseHook() {
    override fun init() {
        getMethodByDesc("Lcom/tencent/mobileqq/profilecard/vas/VasProfileTemplateController;->a(Lcom/tencent/mobileqq/data/Card;I)V")
            .hookBefore { param ->
                param.result = null
            }
    }
}
