package org.kitsunepie.qassistant.app.hook.normal.simplify

import android.view.View
import com.github.kyuubiran.ezxhelper.utils.emptyParam
import com.github.kyuubiran.ezxhelper.utils.findMethod
import com.github.kyuubiran.ezxhelper.utils.hookBefore
import org.kitsunepie.qassistant.annotations.NormalHookEntry
import org.kitsunepie.qassistant.app.hook.base.BaseHook
import org.kitsunepie.qassistant.app.util.ClassPointer
import org.kitsunepie.qassistant.app.util.clazz

@NormalHookEntry
object PreventQBossAdLoad : BaseHook() {
    override fun init() {
        findMethod(ClassPointer.QbossADImmersionBannerManager.clazz!!) {
            returnType == View::class.java && emptyParam
        }.hookBefore { param ->
            param.result = null
        }
    }
}
