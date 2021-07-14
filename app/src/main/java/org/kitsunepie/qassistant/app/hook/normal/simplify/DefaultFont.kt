package org.kitsunepie.qassistant.app.hook.normal.simplify

import android.view.View
import com.github.kyuubiran.ezxhelper.utils.*
import org.kitsunepie.qassistant.annotations.NormalHookEntry
import org.kitsunepie.qassistant.app.hook.base.BaseSwitchHook
import org.kitsunepie.qassistant.app.util.ClassPointer
import org.kitsunepie.qassistant.app.util.clazz

@NormalHookEntry
object DefaultFont : BaseSwitchHook() {
    override fun init() {
        ClassPointer.TextItemBuilder.clazz?.let {
            findMethodByCondition(it) { m ->
                m.name == "a" && !m.isStatic &&
                m.returnType == Void.TYPE &&
                m.parameterTypes.contentDeepEquals(
                    arrayOf(
                        View::class.java,
                        loadClass("com.tencent.mobileqq.data.ChatMessage")
                    )
                )
            }.also { m ->
                m.hookReplace { null }
            }
        }
    }

    override val title: String = "隐藏字体特效"
}
