package com.qnotifiedx.app.util

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import java.lang.reflect.Constructor
import java.lang.reflect.Field
import java.lang.reflect.Method
import java.lang.reflect.Modifier

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
 * @param clzName 类名
 * @param clzLoader 类加载器 默认使用模块的类加载器
 * @return 被加载的类
 */
fun loadClass(clzName: String, clzLoader: ClassLoader = mClzLoader): Class<*> {
    return clzLoader.loadClass(clzName)
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
 * 获取实例化对象的所有方法
 * @return 方法数组
 */
fun Any.getMethods(): Array<Method> {
    return this::class.java.declaredMethods
}

/**
 * 获取类的所有属性
 * @param clzName 类名
 * @return 属性数组
 */
fun getFields(clzName: String): Array<Field> {
    return loadClass(clzName).declaredFields
}

/**
 * 扩展函数 通过类获取单个方法
 * @param methodName 方法名
 * @param returnType 方法返回值
 * @param argTypes 方法形参表类型
 */
fun Class<*>.getMethodByClz(
    methodName: String,
    returnType: Class<*> = Void.TYPE,
    argTypes: Array<out Class<*>> = arrayOf()
): Method? {
    var clz = this
    do {
        if (methodName.isEmpty()) return null
        for (m in clz.declaredMethods) {
            if (m.name != methodName) continue
            if (m.returnType != returnType) continue
            for (type in m.parameterTypes.withIndex()) {
                if (type != argTypes[type.index]) continue
            }
            return m
        }
        if (clz.superclass == null) return null
        clz = clz.superclass
    } while (true)
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
    argTypes: Array<out Class<*>> = arrayOf()
): Method? {
    return loadClass(clzName).getMethodByClz(
        methodName,
        returnType = returnType,
        argTypes = argTypes
    )
}

/**
 * 扩展函数 通过对象获取单个方法
 * @param methodName 方法名
 * @param returnType 方法返回值
 * @param argTypes 方法形参表类型
 */
fun Any.getMethodByObject(
    methodName: String,
    returnType: Class<*> = Void.TYPE,
    argTypes: Array<out Class<*>> = arrayOf()
): Method? {
    return this.javaClass.getMethodByClz(methodName, returnType = returnType, argTypes = argTypes)
}

/**
 * 扩展函数 通过类获取单个属性
 * @param fieldName 属性名
 * @param fieldType 属性类型
 */
fun Class<*>.getField(fieldName: String, fieldType: Class<*>? = null): Field? {
    if (fieldName.isEmpty()) return null
    var clz: Class<*> = this
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
 * 扩展函数 通过对象获取单个属性
 * @param fieldName 属性名
 * @param fieldType 属性类型
 */
fun Any.getField(fieldName: String, fieldType: Class<*>? = null): Field? {
    return this.javaClass.getField(fieldName, fieldType)
}

/**
 * 扩展函数 通过对象 获取对象中的对象
 * @param name 对象名称
 * @param type 类型
 */
fun Any.getObjectOrNull(name: String, type: Class<*>? = null): Any? {
    return try {
        val f = this.javaClass.getField(name, type)
        f?.isAccessible = true
        f?.get(this)
    } catch (e: Exception) {
        Log.e(e)
        null
    }
}

/**
 * 通过Class获取目标实例化对象中的对象
 * @param targetObj 目标实例化的对象
 * @param objName 需要获取的对象名
 */
fun Class<*>.getObjectOrNull(targetObj: Any, objName: String): Any? {
    return try {
        val f = targetObj.getField(objName, this)
        f?.isAccessible = true
        f?.get(targetObj)
    } catch (e: Exception) {
        Log.e(e)
        null
    }
}

/**
 * 扩展函数 调用对象的方法
 * @param methodName 方法名
 * @param args 参数表 可空
 * @param argTypes 参数类型 可空
 * @param returnType 返回值类型
 * @return 函数调用后的返回值
 * @throws NoSuchMethodException 当args的长度与argTypes的长度不符时抛出
 */
fun Any.invokeMethod(
    methodName: String,
    args: Array<out Any>? = null,
    argTypes: Array<out Class<*>> = arrayOf(),
    returnType: Class<*> = Void.TYPE
): Any? {
    if (args?.size != argTypes.size) throw NoSuchMethodException("Method args size must equals argTypes size!")
    val m: Method?
    return if (args.isNullOrEmpty()) {
        m = this.getMethodByObject(methodName, returnType)
        m?.isAccessible = true
        m?.invoke(this)
    } else {
        m = argTypes.let { this.getMethodByObject(methodName, returnType, it) }
        m?.isAccessible = true
        m?.invoke(this, *args)
    }
}

/**
 * 扩展函数 创建新的实例化对象
 * @param args 构造函数的参数表
 * @param argTypes 构造函数的参数类型
 * @return 成功时返回实例化的对象 失败时返回null
 * @throws NoSuchMethodException 当args的长度与argTypes的长度不符时抛出
 */
fun Class<*>.newInstance(
    args: Array<out Any>? = null,
    argTypes: Array<out Class<*>> = arrayOf()
): Any? {
    if (args?.size != argTypes.size) throw NoSuchMethodException("Method args size must equals argTypes size!")
    return try {
        val constructor: Constructor<*> =
            if (!argTypes.isNullOrEmpty())
                this.getDeclaredConstructor(*argTypes)
            else
                this.getDeclaredConstructor()
        if (args.isNullOrEmpty()) {
            constructor.newInstance()
        } else {
            constructor.newInstance(*args)
        }
    } catch (e: Exception) {
        Log.e(e)
        null
    }
}

/**
 * 扩展属性 判断方法是否为Static
 */
val Method.isStatic: Boolean
    get() = Modifier.isStatic(this.modifiers)

/**
 * 扩展属性 判断方法是否为Public
 */
val Method.isPublic: Boolean
    get() = Modifier.isPublic(this.modifiers)

/**
 * 扩展属性 判断方法是否为Private
 */
val Method.isPrivate: Boolean
    get() = Modifier.isPrivate(this.modifiers)

/**
 * 扩展属性 判断方法是否为Final
 */
val Method.isFinal: Boolean
    get() = Modifier.isFinal(this.modifiers)

/**
 * 深拷贝一个对象
 * @param srcObj 源对象
 * @param newObj 新对象
 * @return 成功返回拷贝后的对象 失败返回null
 */
fun <T> fieldCpy(srcObj: T, newObj: T): T? {
    return try {
        var clz: Class<*> = srcObj!!::class.java
        var fields: Array<Field>
        while (Object::class.java != clz) {
            fields = clz.declaredFields
            for (f in fields) {
                f.isAccessible = true
                f.set(newObj, f.get(srcObj))
            }
            clz = clz.superclass
        }
        newObj
    } catch (e: Exception) {
        Log.e(e)
        null
    }
}