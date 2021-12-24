package org.kitsunepie.qassistant.app.hook.normal.simplify

import android.view.View
import com.github.kyuubiran.ezxhelper.utils.findMethod
import com.github.kyuubiran.ezxhelper.utils.hookReplace
import com.github.kyuubiran.ezxhelper.utils.isStatic
import com.github.kyuubiran.ezxhelper.utils.loadClass
import org.kitsunepie.qassistant.annotations.NormalHookEntry
import org.kitsunepie.qassistant.app.hook.base.BaseHook
import org.kitsunepie.qassistant.app.util.ClassPointer
import org.kitsunepie.qassistant.app.util.clazz

@NormalHookEntry
object DefaultFont : BaseHook() {
    override fun init() {
        ClassPointer.TextItemBuilder.clazz?.let {
            findMethod(it) {
                name == "a" && !isStatic &&
                    returnType == Void.TYPE &&
                    parameterTypes.contentDeepEquals(
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
}
