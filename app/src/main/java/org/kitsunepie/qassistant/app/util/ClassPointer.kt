package org.kitsunepie.qassistant.app.util

import com.github.kyuubiran.ezxhelper.utils.getFieldByClassOrObject
import com.github.kyuubiran.ezxhelper.utils.loadClass

/**
 * 用于寻找内部类/匿名函数持有的外部类的静态对象(this$0)所指向的类型
 */
enum class ClassPointer {
    QbossADImmersionBannerManager,
    ConversationTitleBtnCtrl,
    UpgradeController1,
    UpgradeController2,
    TextItemBuilder
}

//此处声明包含this$0的类的类名
val ClassPointer.clzName: String
    get() {
        return when (this) {
            ClassPointer.QbossADImmersionBannerManager -> "cooperation.vip.qqbanner.QbossADImmersionBannerManager"
            ClassPointer.ConversationTitleBtnCtrl -> "com.tencent.mobileqq.activity.ConversationTitleBtnCtrl"
            ClassPointer.UpgradeController1 -> "com.tencent.mobileqq.upgrade.UpgradeController"
            ClassPointer.UpgradeController2 -> "com.tencent.mobileqq.app.upgrade.UpgradeController"
            ClassPointer.TextItemBuilder -> "com.tencent.mobileqq.activity.aio.item.TextItemBuilder"
        }
    }

//此处标明类后面的数字
val ClassPointer.clzIndex: IntArray
    get() {
        return when (this) {
            ClassPointer.QbossADImmersionBannerManager -> intArrayOf(1, 2)
            ClassPointer.ConversationTitleBtnCtrl -> intArrayOf(1, 2, 4, 5, 6)
            ClassPointer.UpgradeController1 -> intArrayOf(1, 2)
            ClassPointer.UpgradeController2 -> intArrayOf(1, 2)
            ClassPointer.TextItemBuilder -> intArrayOf(3, 6 ,7, 8, 10)
        }
    }

//使用.clazz来获取类 如果获取失败则返回null
val ClassPointer.clazz: Class<*>?
    get() {
        runCatching {
            return loadClass(this.clzName)
        }
        this.clzIndex.forEach { idx ->
            runCatching {
                return loadClass("${this.clzName}\$${idx}").getFieldByClassOrObject("this$0").type
            }
        }
        return null
    }
