package org.kitsunepie.qassistant.app.hook.normal.simplify

import android.view.View
import com.github.kyuubiran.ezxhelper.utils.emptyParam
import com.github.kyuubiran.ezxhelper.utils.findMethod
import com.github.kyuubiran.ezxhelper.utils.hookBefore
import org.kitsunepie.qassistant.annotations.NormalHookEntry
import org.kitsunepie.qassistant.app.hook.base.BaseHook
import org.kitsunepie.qassistant.app.hook.base.IHookInfo
import org.kitsunepie.qassistant.app.util.ClassPointer
import org.kitsunepie.qassistant.app.util.clazz

@NormalHookEntry
object PreventQBossAdLoad : BaseHook(), IHookInfo {
    override fun init() {
        findMethod(ClassPointer.QbossADImmersionBannerManager.clazz!!) {
            returnType == View::class.java && emptyParam
        }.hookBefore { param ->
            param.result = null
        }
    }

    override val title: String
        get() = "阻止横幅广告加载"

    override val summary: String
        get() = "我不想参加活动！！！"
}
