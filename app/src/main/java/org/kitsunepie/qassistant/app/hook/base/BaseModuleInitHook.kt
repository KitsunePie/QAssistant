package org.kitsunepie.qassistant.app.hook.base

import com.github.kyuubiran.ezxhelper.utils.Log
import org.kitsunepie.qassistant.app.HookInit
import org.kitsunepie.qassistant.app.hook.moduleinit.GetApplication
import org.kitsunepie.qassistant.app.hook.moduleinit.ModuleEntry

/**
 * 模块初始化相关的Hook
 */
interface BaseModuleInitHook : BaseHook {
    companion object {
        private val initHooks = arrayOf(
            GetApplication,
            ModuleEntry
        )

        fun initHooks() {
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
    }
}