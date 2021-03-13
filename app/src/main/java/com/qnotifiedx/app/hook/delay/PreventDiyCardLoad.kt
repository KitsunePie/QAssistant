package com.qnotifiedx.app.hook.delay

import com.qnotifiedx.app.hook.base.BaseDelayHook
import com.qnotifiedx.app.util.getMethods
import com.qnotifiedx.app.util.hookBefore
import com.qnotifiedx.app.util.isPublic
import java.lang.reflect.Method

//阻止DIY名片加载
object PreventDiyCardLoad : BaseDelayHook() {
    override fun init() {
        for (m: Method in getMethods("com.tencent.mobileqq.profilecard.vas.VasProfileTemplateController")) {
            val argTypes = m.parameterTypes
            if (m.name == "a" && argTypes.size == 2 && argTypes[1] == Int::class.java && m.isPublic) {
                m.hookBefore {
                    it.result = null
                }
            }
        }
    }
}