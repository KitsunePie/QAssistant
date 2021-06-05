package com.kitsunepie.qnotifiedx.app.hook.normal.function

import android.widget.EditText
import com.github.kyuubiran.ezxhelper.utils.findMethodByCondition
import com.github.kyuubiran.ezxhelper.utils.getObjectAs
import com.github.kyuubiran.ezxhelper.utils.putObject
import com.kitsunepie.qnotifiedx.annotations.NormalHookEntry
import com.kitsunepie.qnotifiedx.app.hook.base.BaseNormalHook
import com.kitsunepie.qnotifiedx.app.util.hookAfter
import com.kitsunepie.qnotifiedx.app.util.hookBefore

@NormalHookEntry
object UnlockUniqueTitleLength : BaseNormalHook() {
    override val name: String = "解除头衔字数上限"
    override val desc: String = "最大支持18个英文字符(6个中文字符)"

    override fun init() {
        //EditText控制
        findMethodByCondition("com.tencent.biz.troop.EditUniqueTitleActivity") {
            it.name == "doOnCreate"
        }.also { m ->
            m.hookAfter(this) {
                val et = it.thisObject.getObjectAs<EditText>("a", EditText::class.java)
                et.filters = emptyArray()
                it.thisObject.putObject("a", Int.MAX_VALUE, Int::class.java)
            }
        }
        //完成按钮
        findMethodByCondition("com.tencent.biz.troop.EditUniqueTitleActivity") {
            it.name == "a" && it.parameterTypes.size == 2 && it.parameterTypes[1] == Int::class.java
        }.also { m ->
            m.hookBefore(this) {
                it.args[1] = true
            }
        }
    }
}