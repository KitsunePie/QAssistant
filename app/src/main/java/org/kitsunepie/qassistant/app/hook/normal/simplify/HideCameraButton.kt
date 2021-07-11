package org.kitsunepie.qassistant.app.hook.normal.simplify

import com.github.kyuubiran.ezxhelper.utils.findMethodByCondition
import org.kitsunepie.qassistant.annotations.NormalHookEntry
import org.kitsunepie.qassistant.app.hook.base.BaseSwitchHook
import org.kitsunepie.qassistant.app.util.ClassPointer
import org.kitsunepie.qassistant.app.util.clazz
import org.kitsunepie.qassistant.app.util.hookBefore

@NormalHookEntry
object HideCameraButton : BaseSwitchHook() {
    override fun init() {
        findMethodByCondition(ClassPointer.ConversationTitleBtnCtrl.clazz!!) {
            it.name == "a" && it.returnType == Void.TYPE && it.parameterTypes.isEmpty()
        }.hookBefore(this) { param ->
            param.result = null
        }
    }

    override val title: String = "隐藏相机图标"
}