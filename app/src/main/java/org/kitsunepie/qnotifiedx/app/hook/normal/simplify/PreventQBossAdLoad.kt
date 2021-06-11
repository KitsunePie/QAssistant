package org.kitsunepie.qnotifiedx.app.hook.normal.simplify

import android.view.View
import com.github.kyuubiran.ezxhelper.utils.findMethodByCondition
import org.kitsunepie.qnotifiedx.app.hook.base.BaseNormalHook
import org.kitsunepie.qnotifiedx.app.util.ClassPointer
import org.kitsunepie.qnotifiedx.app.util.clazz
import org.kitsunepie.qnotifiedx.app.util.hookBefore

@org.kitsunepie.qnotifiedx.annotations.NormalHookEntry
object PreventQBossAdLoad : BaseNormalHook() {
    override val name: String = "阻止主界面横幅广告加载"
    override val desc: String = "还你一个干净的主界面"

    override fun init() {
        findMethodByCondition(ClassPointer.QbossADImmersionBannerManager.clazz!!) {
            it.returnType == View::class.java && it.parameterTypes.isEmpty()
        }.also { m ->
            m.hookBefore(this) { param ->
                param.result = null
            }
        }
    }
}