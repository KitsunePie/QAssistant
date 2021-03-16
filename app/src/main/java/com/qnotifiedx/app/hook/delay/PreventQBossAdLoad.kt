package com.qnotifiedx.app.hook.delay

import android.view.View
import com.qnotifiedx.annotations.DelayHookEntry
import com.qnotifiedx.app.hook.base.BaseDelayHook
import com.qnotifiedx.app.util.*

@DelayHookEntry
object PreventQBossAdLoad : BaseDelayHook() {
    override val name: String = "阻止主界面横幅广告加载"

    override fun init() {
        findMethodByCondition(ClassPointer.QbossADImmersionBannerManager.clazz!!) {
            it.returnType == View::class.java && it.parameterTypes.isEmpty() && it.isStatic
        }.also { m ->
            m.hookBefore {
                if (!enable) return@hookBefore
                it.result = null
            }
        }
    }
}