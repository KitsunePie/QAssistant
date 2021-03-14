package com.qnotifiedx.app.util

import com.qnotifiedx.app.hook.base.BaseHook
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XC_MethodReplacement
import de.robv.android.xposed.XposedBridge
import de.robv.android.xposed.callbacks.XCallback
import java.lang.reflect.Method


/**
 * 扩展函数 hook方法
 * @param hookCallback XC_MethodHook
 */
fun Method.hookMethod(hookCallback: XC_MethodHook) {
    XposedBridge.hookMethod(this, hookCallback)
}

/**
 * 扩展函数 hook方法执行前
 * @param priority 优先级 默认50
 * @param hook hook具体实现
 */
fun Method.hookBefore(
    baseHook: BaseHook,
    priority: Int = XCallback.PRIORITY_DEFAULT,
    hook: (XC_MethodHook.MethodHookParam) -> Unit
) {
    this.hookMethod(object : XC_MethodHook(priority) {
        override fun beforeHookedMethod(param: MethodHookParam) {
            try {
                if (!baseHook.enable) return
                hook(param)
            } catch (thr: Throwable) {
                Log.t(thr)
            }
        }
    })
}

/**
 * 扩展函数 hook方法执行后
 * @param priority 优先级 默认50
 * @param hook hook具体实现
 */
fun Method.hookAfter(
    baseHook: BaseHook,
    priority: Int = XCallback.PRIORITY_DEFAULT,
    hook: (XC_MethodHook.MethodHookParam) -> Unit
) {
    this.hookMethod(object : XC_MethodHook(priority) {
        override fun afterHookedMethod(param: MethodHookParam) {
            try {
                if (!baseHook.enable) return
                hook(param)
            } catch (thr: Throwable) {
                Log.t(thr)
            }
        }
    })
}

/**
 * 扩展函数 替换方法
 * @param priority 优先级 默认50
 * @param hook hook具体实现
 */
fun Method.replaceHook(
    baseHook: BaseHook,
    priority: Int = XCallback.PRIORITY_DEFAULT,
    hook: (XC_MethodHook.MethodHookParam) -> Unit
) {
    this.hookMethod(object : XC_MethodReplacement(priority) {
        override fun replaceHookedMethod(param: MethodHookParam): Any {
            return try {
                if (!baseHook.enable) return XposedBridge.invokeOriginalMethod(param.method, param.thisObject, param.args)
                hook(param)
            } catch (thr: Throwable) {
                Log.t(thr)
            }
        }
    })
}