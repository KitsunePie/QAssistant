package com.kitsunepie.qnotifiedx.app.util

import com.github.kyuubiran.ezxhelper.utils.getFieldByClzOrObj
import com.github.kyuubiran.ezxhelper.utils.loadClass

/**
 * 用于寻找内部类/匿名函数持有的外部类的静态对象(this$0)所指向的类型
 */
enum class ClassPointer {
    QbossADImmersionBannerManager,
}

//此处声明包含this$0的类的类名
val ClassPointer.clzName: String
    get() {
        return when (this) {
            ClassPointer.QbossADImmersionBannerManager -> "cooperation.vip.qqbanner.QbossADImmersionBannerManager"
        }
    }

//此处标明类后面的数字
val ClassPointer.clzIndex: Array<Int>
    get() {
        return when (this) {
            ClassPointer.QbossADImmersionBannerManager -> arrayOf(1, 2)
        }
    }

//使用.clazz来获取类 如果获取失败则返回null
val ClassPointer.clazz: Class<*>?
    get() {
        return try {
            var clz: Class<*>? = null
            try {
                clz = loadClass(this.clzName)
            } catch (ignored: Exception) {
            }
            if (clz == null) {
                for (i in this.clzIndex) {
                    try {
                        clz = loadClass("${this.clzName}\$${this.clzIndex}")
                    } catch (ignored: Exception) {
                    }
                    if (clz != null) break
                }
            }
            if (clz == null) return null
            clz.getFieldByClzOrObj("this$0").type
        } catch (e: Exception) {
            null
        }
    }
