package com.kitsunepie.qnotifiedx.app.util

import com.github.kyuubiran.ezxhelper.utils.Log
import com.kitsunepie.qnotifiedx.app.hook.base.BaseHook
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XC_MethodReplacement
import de.robv.android.xposed.XposedBridge
import de.robv.android.xposed.callbacks.XCallback
import java.lang.reflect.Member

/**
 * 扩展函数 hook方法
 * @param hookCallback XC_MethodHook
 */
fun Member.hookMethod(hookCallback: XC_MethodHook) {
    XposedBridge.hookMethod(this, hookCallback)
}

/**
 * 扩展函数 hook方法执行前
 * @param baseHook Hook基类
 * @param priority 优先级 默认50
 * @param hook hook具体实现
 */
fun Member.hookBefore(
    baseHook: BaseHook,
    priority: Int = XCallback.PRIORITY_DEFAULT,
    hook: (XC_MethodHook.MethodHookParam) -> Unit
) {
    this.hookMethod(object : XC_MethodHook(priority) {
        override fun beforeHookedMethod(param: MethodHookParam) {
            try {
                if (!baseHook.isEnabled) return
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
fun Member.hookAfter(
    baseHook: BaseHook,
    priority: Int = XCallback.PRIORITY_DEFAULT,
    hook: (XC_MethodHook.MethodHookParam) -> Unit
) {
    this.hookMethod(object : XC_MethodHook(priority) {
        override fun afterHookedMethod(param: MethodHookParam) {
            try {
                if (!baseHook.isEnabled) return
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
fun Member.replaceHook(
    baseHook: BaseHook,
    priority: Int = XCallback.PRIORITY_DEFAULT,
    hook: (XC_MethodHook.MethodHookParam) -> Any
) {
    this.hookMethod(object : XC_MethodReplacement(priority) {
        override fun replaceHookedMethod(param: MethodHookParam): Any {
            return try {
                if (!baseHook.isEnabled) return XposedBridge.invokeOriginalMethod(
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