package org.kitsunepie.qassistant.app.hook.normal.function

import com.github.kyuubiran.ezxhelper.utils.findMethod
import com.github.kyuubiran.ezxhelper.utils.hookBefore
import org.kitsunepie.qassistant.annotations.NormalHookEntry
import org.kitsunepie.qassistant.app.hook.base.BaseHook
import org.kitsunepie.qassistant.app.hook.base.IHookInfo
import org.kitsunepie.qassistant.app.util.ClassPointer
import org.kitsunepie.qassistant.app.util.clazz

@NormalHookEntry
object PreventUpdate : BaseHook(),IHookInfo {
    override val needReboot: Boolean = true

    override fun init() {
        val clz = ClassPointer.UpgradeController1.clazz ?: ClassPointer.UpgradeController2.clazz!!
        findMethod(clz) {
            name == "a" && returnType.name.contains("UpgradeDetailWrapper") && parameterTypes.isEmpty()
        }.hookBefore { param ->
            param.result = null
        }
    }

    override val title: String
        get() = "阻止更新检测"

    override val summary: String
        get() = "我是老版本魔人"
}
