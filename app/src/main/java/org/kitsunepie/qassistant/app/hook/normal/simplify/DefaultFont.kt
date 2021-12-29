package org.kitsunepie.qassistant.app.hook.normal.simplify

import android.view.View
import com.github.kyuubiran.ezxhelper.utils.findMethod
import com.github.kyuubiran.ezxhelper.utils.hookReplace
import com.github.kyuubiran.ezxhelper.utils.isStatic
import com.github.kyuubiran.ezxhelper.utils.loadClass
import org.kitsunepie.qassistant.annotations.NormalHookEntry
import org.kitsunepie.qassistant.app.hook.base.BaseHook
import org.kitsunepie.qassistant.app.hook.base.IHookInfo
import org.kitsunepie.qassistant.app.util.ClassPointer
import org.kitsunepie.qassistant.app.util.clazz

@NormalHookEntry
object DefaultFont : BaseHook(), IHookInfo {
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

    override val title: String
        get() = "默认字体"

    override val summary: String
        get() = "别再给我用花里胡哨的字体了！！！"
}
