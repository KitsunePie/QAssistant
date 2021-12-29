package org.kitsunepie.qassistant.app.hook.normal.function

import android.widget.EditText
import com.github.kyuubiran.ezxhelper.utils.*
import org.kitsunepie.qassistant.annotations.NormalHookEntry
import org.kitsunepie.qassistant.app.hook.base.BaseHook
import org.kitsunepie.qassistant.app.hook.base.IHookInfo

@NormalHookEntry
object UnlockUniqueTitleLength : BaseHook(),IHookInfo {
    override fun init() {
        //EditText控制
        getMethodByDesc("Lcom/tencent/biz/troop/EditUniqueTitleActivity;->doOnCreate(Landroid/os/Bundle;)Z")
            .hookAfter { param ->
                val et = param.thisObject.getObjectAs<EditText>("a", EditText::class.java)
                et.filters = emptyArray()
                param.thisObject.putObject("a", Int.MAX_VALUE, Int::class.java)
            }
        //完成按钮
        getMethodByDesc("Lcom/tencent/biz/troop/EditUniqueTitleActivity;->a(Lcom/tencent/biz/troop/EditUniqueTitleActivity;Z)V")
            .hookBefore { param ->
                param.args[1] = true
            }
    }

    override val title: String
        get() = "解锁头衔长度限制"

    override val summary: String
        get() = "但是最长还是只有18个字符"
}
