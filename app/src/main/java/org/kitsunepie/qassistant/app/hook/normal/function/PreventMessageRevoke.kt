package org.kitsunepie.qassistant.app.hook.normal.function

import org.kitsunepie.qassistant.app.hook.base.BaseHook
import org.kitsunepie.qassistant.app.hook.base.IHookInfo

object PreventMessageRevoke : BaseHook(), IHookInfo {
    override fun init() {
    }

    override val title: String
        get() = "阻止消息撤回"

    override val summary: String
        get() = "听话，让我康康！"
}
