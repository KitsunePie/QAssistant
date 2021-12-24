package org.kitsunepie.qassistant.app.hook.base

import com.github.kyuubiran.ezxhelper.utils.Log
import org.kitsunepie.qassistant.app.HookInit
import org.kitsunepie.qassistant.app.hook.moduleinit.GetApplication
import org.kitsunepie.qassistant.app.hook.moduleinit.ModuleEntry
import org.kitsunepie.qassistant.core.processctrl.ProcessInfo.isCurrentProc
import org.kitsunepie.qassistant.gen.DelayHooks

/**
 * 模块初始化相关的Hook
 */
object HookInitializer {
    private val moduleHooks = arrayOf(
        GetApplication,
        ModuleEntry
    )

    fun initModuleHooks() {
        moduleHooks.forEach { h ->
            if (h.isInited || HookInit.processName != HookInit.packageName) return@forEach
            try {
                h.init()
                h.isInited = true
                Log.i("Inited module hook: ${h.javaClass.name}")
            } catch (thr: Throwable) {
                Log.e(thr, "Init failed module hook: ${h.javaClass.name}")
                throw thr
            }
        }
    }

    private val normalHooks by lazy {
        DelayHooks.getAnnotatedItemClassList()
    }

    fun initNormalHooks() {
        normalHooks.forEach hook@{ h ->
            if (h.isInited) return@hook
            h.targetProc.forEach proc@{ p ->
                if (!p.isCurrentProc) return@proc
                try {
                    h.init()
                    h.isInited = true
                    Log.i("Init normal hook: ${h.javaClass.name}")
                } catch (thr: Throwable) {
                    Log.e(thr, "Init failed normal hook: ${h.javaClass.name}")
                }
            }
        }
    }
}
