package org.kitsunepie.qassistant.app.hook.normal.simplify

import org.kitsunepie.qassistant.app.hook.base.BaseHook
import org.kitsunepie.qassistant.app.hook.base.IHookInfo

object RemoveGroupApp : BaseHook(), IHookInfo {
    override fun init() {
    }

    override val title: String
        get() = "移除群组应用"
}
