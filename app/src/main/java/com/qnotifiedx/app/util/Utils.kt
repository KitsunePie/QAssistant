package com.qnotifiedx.app.util

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import java.lang.reflect.Field
import java.lang.reflect.Method

val mainHandler: Handler by lazy {
    Handler(Looper.getMainLooper())
}

val runtimeProcess: Runtime by lazy {
    Runtime.getRuntime()
}

//模块的类加载器
lateinit var mClzLoader: ClassLoader


/**
 * 将函数放到主线程执行 如UI更新、显示Toast等
 * @param r 需要执行的内容
 */
fun runOnMainThread(r: Runnable) {
    if (Looper.myLooper() == Looper.getMainLooper()) {
        r.run()
    } else {
        mainHandler.post(r)
    }
}

/**
 * 扩展函数 显示一个Toast
 * @param msg Toast显示的消息
 * @param length Toast显示的长度
 */
fun Context.showToast(msg: String, length: Int = Toast.LENGTH_SHORT) {
    runOnMainThread {
        Toast.makeText(this, msg, length).show()
    }
}

/**
 * 通过模块加载类
 * @return 被加载的类
 */
fun loadClass(clzName: String): Class<*> {
    return mClzLoader.loadClass(clzName)
}

/**
 * 获取类的所有方法
 * @param clzName 类名
 * @return 方法数组
 */
fun getMethods(clzName: String): Array<Method> {
    return loadClass(clzName).declaredMethods
}

/**
 * 获取类的所有方法
 * @param clazz 目标类
 * @return 方法数组
 */
fun getMethods(clazz: Class<*>): Array<Method> {
    return clazz.declaredMethods
}

/**
 * 获取类的所有属性
 * @param clzName 类名
 * @return 属性数组
 */
fun getFields(clzName: String): Array<Field>? {
    return loadClass(clzName).declaredFields
}

/**
 * 获取类的所有属性
 * @param clazz 目标类
 * @return 属性数组
 */
fun getFields(clazz: Class<*>): Array<Field>? {
    return clazz.declaredFields
}

/**
 * 获取单个方法
 * @param clazz 目标类
 * @param methodName 方法名
 * @param returnType 方法返回值
 * @param argTypes 方法形参表类型
 */
fun getMethod(
    clazz: Class<*>,
    methodName: String,
    returnType: Class<*> = Void.TYPE,
    vararg argTypes: Class<*>?
): Method? {
    if (methodName.isEmpty()) return null
    for (m in getMethods(clazz)) {
        if (m.name != methodName) continue
        if (m.returnType != returnType) continue
        for (type in m.parameterTypes.withIndex()) {
            if (type != argTypes[type.index]) continue
        }
        return m
    }
    return null
}

/**
 * 获取单个方法
 * @param clzName 类名
 * @param methodName 方法名
 * @param returnType 方法返回值
 * @param argTypes 方法形参表类型
 */
fun getMethod(
    clzName: String,
    methodName: String,
    returnType: Class<*> = Void.TYPE,
    vararg argTypes: Class<*>?
): Method? {
    return getMethod(loadClass(clzName), methodName, returnType, *argTypes)
}

/**
 * 获取单个方法
 * @param obj 实例对象
 * @param methodName 方法名
 * @param returnType 方法返回值
 * @param argTypes 方法形参表类型
 */
fun getMethod(
    obj: Any,
    methodName: String,
    returnType: Class<*> = Void.TYPE,
    vararg argTypes: Class<*>?
): Method? {
    return getMethod(obj::class.java, methodName, returnType, *argTypes)
}

/**
 * 获取单个属性
 * @param clazz 目标类
 * @param fieldName 属性名
 * @param fieldType 属性类型
 */
fun getField(clazz: Class<*>, fieldName: String, fieldType: Class<*>? = null): Field? {
    if (fieldName.isEmpty()) return null
    var clz: Class<*> = clazz
    do {
        for (f in clz.declaredFields) {
            if ((fieldType == null || f.type == fieldType) && (f.name == fieldName)) {
                f.isAccessible = true
                return f
            }
        }
        if (clz.superclass == null) return null
        clz = clz.superclass
    } while (true)
}

/**
 * 获取实例化对象中的对象
 * @param obj 实例化对象
 * @param objName 对象名称
 */
inline fun <reified T> getObjectOrNull(obj: Any, objName: String): T? {
    return try {
        val f = getField(obj::class.java, objName, T::class.java)
        f?.isAccessible = true
        f?.get(obj) as T
    } catch (e: Exception) {
        null
    }
}