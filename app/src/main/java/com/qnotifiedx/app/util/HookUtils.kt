package com.qnotifiedx.app.util

import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XC_MethodReplacement
import de.robv.android.xposed.XposedBridge
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
 * @param hook hook具体实现
 */
fun Method.hookBefore(hook: (XC_MethodHook.MethodHookParam) -> Unit) {
    this.hookMethod(object : XC_MethodHook() {
        override fun beforeHookedMethod(param: MethodHookParam?) {
            try {
                hook(param!!)
            } catch (thr: Throwable) {
                Log.t(thr)
            }
        }
    })
}

/**
 * 扩展函数 hook方法执行前
 * @param priority 优先级
 * @param hook hook具体实现
 */
fun Method.hookBefore(priority: Int, hook: (XC_MethodHook.MethodHookParam) -> Unit) {
    this.hookMethod(object : XC_MethodHook(priority) {
        override fun beforeHookedMethod(param: MethodHookParam?) {
            try {
                hook(param!!)
            } catch (thr: Throwable) {
                Log.t(thr)
            }
        }
    })
}

/**
 * 扩展函数 hook方法执行后
 * @param hook hook具体实现
 */
fun Method.hookAfter(hook: (XC_MethodHook.MethodHookParam) -> Unit) {
    this.hookMethod(object : XC_MethodHook() {
        override fun afterHookedMethod(param: MethodHookParam?) {
            try {
                hook(param!!)
            } catch (thr: Throwable) {
                Log.t(thr)
            }
        }
    })
}

/**
 * 扩展函数 hook方法执行后
 * @param priority 优先级
 * @param hook hook具体实现
 */
fun Method.hookAfter(priority: Int, hook: (XC_MethodHook.MethodHookParam) -> Unit) {
    this.hookMethod(object : XC_MethodHook(priority) {
        override fun afterHookedMethod(param: MethodHookParam?) {
            try {
                hook(param!!)
            } catch (thr: Throwable) {
                Log.t(thr)
            }
        }
    })
}

/**
 * 扩展函数 替换方法
 * @param priority 优先级
 * @param hook hook具体实现
 */
fun Method.replaceHook(priority: Int, hook: (XC_MethodHook.MethodHookParam) -> Unit) {
    this.hookMethod(object : XC_MethodReplacement(priority) {
        override fun replaceHookedMethod(param: MethodHookParam?): Any {
            return try {
                hook(param!!)
            } catch (thr: Throwable) {
                Log.t(thr)
            }
        }
    })
}

/**
 * 扩展函数 替换方法
 * @param hook hook具体实现
 */
fun Method.replaceHook(hook: (XC_MethodHook.MethodHookParam) -> Unit) {
    this.hookMethod(object : XC_MethodReplacement() {
        override fun replaceHookedMethod(param: MethodHookParam?): Any {
            return try {
                hook(param!!)
            } catch (thr: Throwable) {
                Log.t(thr)
            }
        }
    })
}