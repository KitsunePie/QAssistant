package org.kitsunepie.qassistant.app.hook.normal.simplify

import org.kitsunepie.qassistant.app.hook.base.BaseSwitchHook

object RemoveGroupApp : BaseSwitchHook() {
    override fun init() {
    }

    override var enable: Boolean = false

    override var title: String = "移除群组应用"
}