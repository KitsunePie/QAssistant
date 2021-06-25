package org.kitsunepie.qassistant.app.hook.normal.function

import org.kitsunepie.qassistant.app.hook.base.BaseSwitchHook

object PreventMessageRevoke : BaseSwitchHook() {
    override fun init() {
    }

    override var enable: Boolean = false

    override var title: String = "防止消息撤回"
}