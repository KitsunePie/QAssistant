package org.kitsunepie.qassistant.app.util

import com.github.kyuubiran.ezxhelper.utils.Log
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XC_MethodReplacement
import de.robv.android.xposed.XposedBridge
import de.robv.android.xposed.callbacks.XCallback
import org.kitsunepie.qassistant.app.hook.base.BaseHook
import java.lang.reflect.Constructor
import java.lang.reflect.Method

/**
 * 扩展函数 hook方法
 * @param hookCallback XC_MethodHook
 */
fun Method.hookMethod(hookCallback: XC_MethodHook) {
    XposedBridge.hookMethod(this, hookCallback)
}

fun Constructor<*>.hookMethod(hookCallback: XC_MethodHook) {
    XposedBridge.hookMethod(this, hookCallback)
}

/**
 * 扩展函数 hook方法执行前
 * @param baseHook Hook基类
 * @param priority 优先级 默认50
 * @param hook hook具体实现
 */
fun Method.hookBefore(
    baseHook: BaseHook,
    priority: Int = XCallback.PRIORITY_DEFAULT,
    hook: (param: XC_MethodHook.MethodHookParam) -> Unit
) {
    this.hookMethod(object : XC_MethodHook(priority) {
        override fun beforeHookedMethod(param: MethodHookParam) {
            try {
                if (!baseHook.isActivated()) return
                hook(param)
            } catch (thr: Throwable) {
                Log.t(thr)
            }
        }
    })
}

fun Constructor<*>.hookBefore(
    baseHook: BaseHook,
    priority: Int = XCallback.PRIORITY_DEFAULT,
    hook: (param: XC_MethodHook.MethodHookParam) -> Unit
) {
    this.hookMethod(object : XC_MethodHook(priority) {
        override fun beforeHookedMethod(param: MethodHookParam) {
            try {
                if (!baseHook.isActivated()) return
                hook(param)
            } catch (thr: Throwable) {
                Log.t(thr)
            }
        }
    })
}

/**
 * 扩展函数 hook方法执行后
 * @param baseHook Hook基类
 * @param priority 优先级 默认50
 * @param hook hook具体实现
 */
fun Method.hookAfter(
    baseHook: BaseHook,
    priority: Int = XCallback.PRIORITY_DEFAULT,
    hook: (param: XC_MethodHook.MethodHookParam) -> Unit
) {
    this.hookMethod(object : XC_MethodHook(priority) {
        override fun afterHookedMethod(param: MethodHookParam) {
            try {
                if (!baseHook.isActivated()) return
                hook(param)
            } catch (thr: Throwable) {
                Log.t(thr)
            }
        }
    })
}

fun Constructor<*>.hookAfter(
    baseHook: BaseHook,
    priority: Int = XCallback.PRIORITY_DEFAULT,
    hook: (param: XC_MethodHook.MethodHookParam) -> Unit
) {
    this.hookMethod(object : XC_MethodHook(priority) {
        override fun afterHookedMethod(param: MethodHookParam) {
            try {
                if (!baseHook.isActivated()) return
                hook(param)
            } catch (thr: Throwable) {
                Log.t(thr)
            }
        }
    })
}

/**
 * 扩展函数 替换方法
 * @param baseHook Hook基类
 * @param priority 优先级 默认50
 * @param hook hook具体实现
 */
fun Method.hookReplace(
    baseHook: BaseHook,
    priority: Int = XCallback.PRIORITY_DEFAULT,
    hook: (param: XC_MethodHook.MethodHookParam) -> Any?
) {
    this.hookMethod(object : XC_MethodReplacement(priority) {
        override fun replaceHookedMethod(param: MethodHookParam): Any? {
            return try {
                if (!baseHook.isActivated()) return XposedBridge.invokeOriginalMethod(
                    param.method,
                    param.thisObject,
                    param.args
                )
                hook(param)
            } catch (thr: Throwable) {
                Log.t(thr)
            }
        }
    })
}

fun Constructor<*>.hookReplace(
    baseHook: BaseHook,
    priority: Int = XCallback.PRIORITY_DEFAULT,
    hook: (param: XC_MethodHook.MethodHookParam) -> Any?
) {
    this.hookMethod(object : XC_MethodReplacement(priority) {
        override fun replaceHookedMethod(param: MethodHookParam): Any? {
            return try {
                if (!baseHook.isActivated()) return XposedBridge.invokeOriginalMethod(
                    param.method,
                    param.thisObject,
                    param.args
                )
                hook(param)
            } catch (thr: Throwable) {
                Log.t(thr)
            }
        }
    })
}