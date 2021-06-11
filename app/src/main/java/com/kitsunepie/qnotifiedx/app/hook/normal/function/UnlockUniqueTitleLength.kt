package com.kitsunepie.qnotifiedx.app.hook.normal.function

import android.widget.EditText
import com.github.kyuubiran.ezxhelper.utils.getMethodBySig
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
        getMethodBySig("Lcom/tencent/biz/troop/EditUniqueTitleActivity;->doOnCreate(Landroid/os/Bundle;)Z").also { m ->
            m.hookAfter(this) { param ->
                val et = param.thisObject.getObjectAs<EditText>("a", EditText::class.java)
                et.filters = emptyArray()
                param.thisObject.putObject("a", Int.MAX_VALUE, Int::class.java)
            }
        }
        //完成按钮
        getMethodBySig("Lcom/tencent/biz/troop/EditUniqueTitleActivity;->a(Lcom/tencent/biz/troop/EditUniqueTitleActivity;Z)V").also { m ->
            m.hookBefore(this) { param ->
                param.args[1] = true
            }
        }
    }
}