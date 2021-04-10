package com.qnotifiedx.app.hook.normal.simplify

import android.view.View
import com.github.kyuubiran.ezxhelper.utils.findMethodByCondition
import com.github.kyuubiran.ezxhelper.utils.isStatic
import com.qnotifiedx.annotations.NormalHookEntry
import com.qnotifiedx.app.hook.base.BaseNormalHook
import com.qnotifiedx.app.util.ClassPointer
import com.qnotifiedx.app.util.clazz
import com.qnotifiedx.app.util.hookBefore

@NormalHookEntry
object PreventQBossAdLoad : BaseNormalHook() {
    override val name: String = "阻止主界面横幅广告加载"
    override val desc: String = "还你一个干净的主界面"

    override fun init() {
        findMethodByCondition(ClassPointer.QbossADImmersionBannerManager.clazz!!) {
            it.returnType == View::class.java && it.parameterTypes.isEmpty() && it.isStatic
        }.also { m ->
            m.hookBefore(this) {
                it.result = null
            }
        }
    }
}