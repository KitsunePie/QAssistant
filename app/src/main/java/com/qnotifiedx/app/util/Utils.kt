package com.qnotifiedx.app.util

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import com.qnotifiedx.app.HookInit
import com.qnotifiedx.app.hook.normal.GetAppContext
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
val mClzLoader: ClassLoader by lazy {
    HookInit.clzLoader
}

//宿主全局Context
val appContext: Context?
    get() = GetAppContext.application

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
 * 扩展函数 获取实例化对象的所有方法
 * 注意 请勿对类使用此函数
 * @return 方法数组
 * @throws IllegalArgumentException 当对象是一个Class时抛出
 */
fun Any.getMethodsByObject(): Array<Method> {
    if (this is Class<*>) throw IllegalArgumentException("Do not use it on a class!")
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
 * 扩展函数 通过类或者对象获取单个方法
 * @param methodName 方法名
 * @param isStatic 是否为静态方法
 * @param returnType 方法返回值
 * @param argTypes 方法形参表类型
 * @throws IllegalArgumentException 当方法名为空时
 */
fun Any.getMethodByClzOrObj(
    methodName: String,
    isStatic: Boolean = false,
    returnType: Class<*> = Void.TYPE,
    argTypes: Array<out Class<*>> = arrayOf()
): Method? {
    if (methodName.isEmpty()) throw IllegalArgumentException("Method name must not be null or empty!")
    var clz = if (this is Class<*>) this else this.javaClass
    do {
        for (m in clz.declaredMethods) {
            if (isStatic && !m.isStatic) continue
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
 * @param isStatic 是否为静态方法
 * @param methodName 方法名
 * @param returnType 方法返回值
 * @param argTypes 方法形参表类型
 * @throws IllegalArgumentException 当方法名为空时
 */
fun getMethod(
    clzName: String,
    isStatic: Boolean = false,
    methodName: String,
    returnType: Class<*> = Void.TYPE,
    argTypes: Array<out Class<*>> = arrayOf()
): Method? {
    if (methodName.isEmpty()) throw IllegalArgumentException("Method name must not be null or empty!")
    return loadClass(clzName).getMethodByClzOrObj(
        methodName,
        isStatic,
        returnType,
        argTypes
    )
}

/**
 * 扩展函数 通过对象或者类获取单个属性
 * @param fieldName 属性名
 * @param isStatic 是否静态类型
 * @param fieldType 属性类型
 * @throws IllegalArgumentException
 */
fun Any.getField(
    fieldName: String,
    isStatic: Boolean = false,
    fieldType: Class<*>? = null
): Field? {
    if (fieldName.isEmpty()) throw IllegalArgumentException("Field name must not be null or empty!")
    var clz: Class<*> = if (this is Class<*>) this else this.javaClass
    do {
        for (f in clz.declaredFields) {
            if (isStatic && !f.isStatic) continue
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
 * 通过类获取静态属性
 * @param fieldName 属性名称
 * @param fieldType 属性类型
 */
fun Class<*>.getStaticFiledByClass(fieldName: String, fieldType: Class<*>? = null): Field? {
    return this.getField(fieldName, true, fieldType)
}

/**
 * 扩展函数 通过对象 获取对象中的对象
 * 注意 请勿对类使用此函数
 * @param objName 对象名称
 * @param type 类型
 * @throws IllegalArgumentException 当目标对象名为空时
 * @throws IllegalArgumentException 当对象是一个Class时
 */
fun Any.getObjectOrNull(objName: String, type: Class<*>? = null): Any? {
    if (objName.isEmpty()) throw IllegalArgumentException("Object name must not be null or empty!")
    if (this is Class<*>) throw IllegalArgumentException("Do not use it on a class!")
    return try {
        val f = this.javaClass.getField(objName, false, type)
        f?.isAccessible = true
        f?.get(this)
    } catch (e: Exception) {
        Log.e(e)
        null
    }
}

/**
 * 扩展函数 通过Class获取目标实例化对象中的对象
 * @param objName 需要获取的对象名
 * @param fieldType 类型
 * @throws IllegalArgumentException 当名字为空时
 */
fun Class<*>.getStaticObjectOrNull(
    objName: String,
    fieldType: Class<*>? = null
): Any? {
    return try {
        if (objName.isEmpty()) throw IllegalArgumentException("Object name must not be null or empty!")
        val f = this.getStaticFiledByClass(objName, fieldType)
        f?.isAccessible = true
        f?.get(null)
    } catch (e: Exception) {
        Log.e(e)
        null
    }
}

/**
 * 扩展函数 通过对象设置属性值
 * 注意 请勿对类使用此函数
 * @param objName 需要设置的对象名称
 * @param value 值
 * @param fieldType 对象类型
 * @throws IllegalArgumentException 当名字为空时
 * @throws IllegalArgumentException 当对象是一个类时
 */
fun Any.putObject(objName: String, value: Any?, fieldType: Class<*>? = null) {
    if (objName.isEmpty()) throw IllegalArgumentException("Object name must not be null or empty!")
    if (this is Class<*>) throw IllegalArgumentException("Do not use it on a class!")
    try {
        val f = this.getField(objName, false, fieldType)
        f?.let {
            it.isAccessible = true
            it.set(this, value)
        }
    } catch (e: Exception) {
        Log.e(e)
    }
}

/**
 * 扩展函数 通过类设置静态属性值
 * @param objName 需要设置的对象名称
 * @param value 值
 * @param fieldType 对象类型
 * @throws IllegalArgumentException 名字为空时
 */
fun Class<*>.putStaticObject(objName: String, value: Any?, fieldType: Class<*>? = null) {
    try {
        if (objName.isEmpty()) throw IllegalArgumentException("Object name must not be null or empty!")
        val f = this.getStaticFiledByClass(objName, fieldType)
        f?.let {
            it.isAccessible = true
            it.set(null, value)
        }
    } catch (e: Exception) {
        Log.e(e)
    }
}

/**
 * 扩展函数 调用对象的方法
 * 注意 请勿对类使用此函数
 * @param methodName 方法名
 * @param args 参数表 可空
 * @param argTypes 参数类型 可空
 * @param returnType 返回值类型 默认为void
 * @return 函数调用后的返回值
 * @throws IllegalArgumentException 当方法名为空时
 * @throws IllegalArgumentException 当args的长度与argTypes的长度不符时
 * @throws IllegalArgumentException 当对象是一个Class时
 */
fun Any.invokeMethod(
    methodName: String,
    args: Array<out Any> = arrayOf(),
    argTypes: Array<out Class<*>> = arrayOf(),
    returnType: Class<*> = Void.TYPE
): Any? {
    if (methodName.isEmpty()) throw IllegalArgumentException("Object name must not be null or empty!")
    if (this is Class<*>) throw IllegalArgumentException("Do not use it on a class!")
    if (args.size != argTypes.size) throw IllegalArgumentException("Method args size must equals argTypes size!")
    val m: Method?
    return if (args.isEmpty()) {
        m = this.getMethodByClzOrObj(methodName, false, returnType)
        m?.isAccessible = true
        m?.invoke(this)
    } else {
        m = argTypes.let { this.getMethodByClzOrObj(methodName, false, returnType, it) }
        m?.isAccessible = true
        m?.invoke(this, *args)
    }
}

/**
 * 扩展函数 调用类的静态方法
 * @param methodName 方法名
 * @param args 参数表 可空
 * @param argTypes 参数类型 可空
 * @param returnType 返回值类型 默认为void
 * @return 函数调用后的返回值
 * @throws IllegalArgumentException 当args的长度与argTypes的长度不符时抛出
 */
fun Class<*>.invokeStaticMethod(
    methodName: String,
    args: Array<out Any> = arrayOf(),
    argTypes: Array<out Class<*>> = arrayOf(),
    returnType: Class<*> = Void.TYPE
): Any? {
    if (args.size != argTypes.size) throw IllegalArgumentException("Method args size must equals argTypes size!")
    val m: Method?
    return if (args.isEmpty()) {
        m = this.getMethodByClzOrObj(methodName, true, returnType)
        m?.isAccessible = true
        m?.invoke(null)
    } else {
        m = argTypes.let { this.getMethodByClzOrObj(methodName, true, returnType, it) }
        m?.isAccessible = true
        m?.invoke(null, *args)
    }
}

/**
 * 扩展函数 创建新的实例化对象
 * @param args 构造函数的参数表
 * @param argTypes 构造函数的参数类型
 * @return 成功时返回实例化的对象 失败时返回null
 * @throws IllegalArgumentException 当args的长度与argTypes的长度不符时抛出
 */
fun Class<*>.newInstance(
    args: Array<out Any> = arrayOf(),
    argTypes: Array<out Class<*>> = arrayOf()
): Any? {
    if (args.size != argTypes.size) throw IllegalArgumentException("Method args size must equals argTypes size!")
    return try {
        val constructor: Constructor<*> =
            if (argTypes.isNotEmpty())
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
 * 扩展属性 判断方法是否为Protected
 */
val Method.isProtected: Boolean
    get() = Modifier.isProtected(this.modifiers)

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
 * 扩展属性 判断属性是否为Static
 */
val Field.isStatic: Boolean
    get() = Modifier.isStatic(this.modifiers)

/**
 * 扩展属性 判断属性是否为Public
 */
val Field.isPublic: Boolean
    get() = Modifier.isPublic(this.modifiers)

/**
 * 扩展属性 判断属性是否为Protected
 */
val Field.isProtected: Boolean
    get() = Modifier.isProtected(this.modifiers)

/**
 * 扩展属性 判断属性是否为Private
 */
val Field.isPrivate: Boolean
    get() = Modifier.isPrivate(this.modifiers)

/**
 * 扩展属性 判断属性是否为Final
 */
val Field.isFinal: Boolean
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