package com.qnotifiedx.app.util

import de.robv.android.xposed.XC_MethodHook
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
 * @param priority 优先级 默认为50 即留空
 * @param hook hook具体实现
 */
fun Method.hookBefore(priority: Int = 50, hook: (XC_MethodHook.MethodHookParam) -> Unit) {
    this.hookMethod(object : XC_MethodHook(priority) {
        override fun beforeHookedMethod(param: MethodHookParam?) {
            hook(param!!)
        }
    })
}

/**
 * 扩展函数 hook方法执行后
 * @param priority 优先级 默认为50 即留空
 * @param hook hook具体实现
 */
fun Method.hookAfter(priority: Int = 50, hook: (XC_MethodHook.MethodHookParam) -> Unit) {
    this.hookMethod(object : XC_MethodHook(priority) {
        override fun afterHookedMethod(param: MethodHookParam?) {
            hook(param!!)
        }
    })
}

