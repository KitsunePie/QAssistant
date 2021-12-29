package org.kitsunepie.qassistant.app.hook.normal.simplify

import com.github.kyuubiran.ezxhelper.utils.emptyParam
import com.github.kyuubiran.ezxhelper.utils.findMethod
import com.github.kyuubiran.ezxhelper.utils.hookBefore
import org.kitsunepie.qassistant.annotations.NormalHookEntry
import org.kitsunepie.qassistant.app.hook.base.BaseHook
import org.kitsunepie.qassistant.app.hook.base.IHookInfo
import org.kitsunepie.qassistant.app.util.ClassPointer
import org.kitsunepie.qassistant.app.util.clazz

@NormalHookEntry
object HideCameraButton : BaseHook(), IHookInfo {
    override val needReboot: Boolean = true

    override fun init() {
        findMethod(ClassPointer.ConversationTitleBtnCtrl.clazz!!) {
            name == "a" && returnType == Void.TYPE && emptyParam
        }.hookBefore { param ->
            param.result = null
        }
    }

    override val title: String
        get() = "隐藏相机按钮"
}
