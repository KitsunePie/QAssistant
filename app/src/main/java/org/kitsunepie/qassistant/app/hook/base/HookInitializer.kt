package org.kitsunepie.qassistant.app.hook.base

import com.github.kyuubiran.ezxhelper.utils.Log
import org.kitsunepie.qassistant.app.HookInit
import org.kitsunepie.qassistant.app.hook.moduleinit.GetApplication
import org.kitsunepie.qassistant.app.hook.moduleinit.ModuleEntry
import org.kitsunepie.qassistant.core.processctrl.ProcessInfo.isCurrentProc

/**
 * 模块初始化相关的Hook
 */
object HookInitializer {
    private val initHooks = arrayOf(
        GetApplication,
        ModuleEntry
    )

    fun initModuleHooks() {
        for (h in initHooks) {
            if (h.isInit || HookInit.processName != HookInit.packageName) continue
            try {
                h.init()
                h.isInit = true
                Log.i("Initialized init hook: ${h.javaClass.name}")
            } catch (thr: Throwable) {
                Log.t(thr, "Initialization failure init hook: ${h.javaClass.name}")
            }
        }
    }

    private val normalHooks =
        org.kitsunepie.qassistant.gen.DelayHooks.getAnnotatedItemClassList()

    fun initNormalHooks() {
        for (h in normalHooks) {
            if (h.isInit) continue
            for (proc in h.targetProc) {
                if (!proc.isCurrentProc) continue
                try {
                    h.init()
                    h.isInit = true
                    Log.i("Initialized normal hook: ${h.javaClass.name}")
                } catch (thr: Throwable) {
                    Log.t(thr, "Initialization failure normal hook: ${h.javaClass.name}")
                }
            }
        }
    }
}