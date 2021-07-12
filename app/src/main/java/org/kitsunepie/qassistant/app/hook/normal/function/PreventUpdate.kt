package org.kitsunepie.qassistant.app.hook.normal.function

import com.github.kyuubiran.ezxhelper.utils.findMethodByCondition
import org.kitsunepie.qassistant.annotations.NormalHookEntry
import org.kitsunepie.qassistant.app.hook.base.BaseSwitchHook
import org.kitsunepie.qassistant.app.util.ClassPointer
import org.kitsunepie.qassistant.app.util.clazz
import org.kitsunepie.qassistant.app.util.hookBefore

@NormalHookEntry
object PreventUpdate : BaseSwitchHook() {
    override val needReboot: Boolean = true

    override fun init() {
        val clz = ClassPointer.UpgradeController1.clazz ?: ClassPointer.UpgradeController2.clazz!!
        findMethodByCondition(clz) {
            it.name == "a" && it.returnType.name.contains("UpgradeDetailWrapper") && it.parameterTypes.isEmpty()
        }.hookBefore(this) {
            it.result = null
        }
    }

    override val title: String = "阻止更新提示"
}