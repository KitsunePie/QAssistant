package org.kitsunepie.qassistant.app.util

import com.github.kyuubiran.ezxhelper.utils.getFieldByClassOrObject
import com.github.kyuubiran.ezxhelper.utils.loadClass

/**
 * 用于寻找内部类/匿名函数持有的外部类的静态对象(this$0)所指向的类型
 */
enum class ClassPointer {
    QbossADImmersionBannerManager,
    ConversationTitleBtnCtrl,
}

//此处声明包含this$0的类的类名
val ClassPointer.clzName: String
    get() {
        return when (this) {
            ClassPointer.QbossADImmersionBannerManager -> "cooperation.vip.qqbanner.QbossADImmersionBannerManager"
            ClassPointer.ConversationTitleBtnCtrl -> "com.tencent.mobileqq.activity.ConversationTitleBtnCtrl"
        }
    }

//此处标明类后面的数字
val ClassPointer.clzIndex: Array<Int>
    get() {
        return when (this) {
            ClassPointer.QbossADImmersionBannerManager -> arrayOf(1, 2)
            ClassPointer.ConversationTitleBtnCtrl -> arrayOf(1, 2, 4, 5, 6)
        }
    }

//使用.clazz来获取类 如果获取失败则返回null
val ClassPointer.clazz: Class<*>?
    get() {
        try {
            return loadClass(this.clzName)
        } catch (ignored: Throwable) {
        }
        this.clzIndex.forEach { idx ->
            try {
                return loadClass("${this.clzName}$${idx}").getFieldByClassOrObject("this$0").type
            } catch (ignored: Throwable) {
            }
        }
        return null
    }
