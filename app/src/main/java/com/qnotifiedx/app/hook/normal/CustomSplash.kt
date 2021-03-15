package com.qnotifiedx.app.hook.normal

import com.qnotifiedx.annotations.NormalHookEntry
import com.qnotifiedx.app.hook.base.BaseNormalHook

//TEST ONLY
@NormalHookEntry
object CustomSplash : BaseNormalHook() {
    override val name: String = "测试HOOK"

    override fun init() {
//        val open =
//            AssetManager::class.java.getDeclaredMethod(
//                "open",
//                String::class.java,
//                Int::class.javaPrimitiveType
//            )
//        open.hookBefore {
//
//        }
    }
}