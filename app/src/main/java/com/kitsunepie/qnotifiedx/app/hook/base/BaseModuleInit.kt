package com.kitsunepie.qnotifiedx.app.hook.base

import com.github.kyuubiran.ezxhelper.utils.Log
import com.kitsunepie.qnotifiedx.app.HookInit
import com.kitsunepie.qnotifiedx.app.hook.moduleinit.GetApplication
import com.kitsunepie.qnotifiedx.app.hook.moduleinit.ModuleEntry

/**
 * 模块初始化相关的Hook
 */
abstract class BaseModuleInit : BaseHook() {

    companion object {
        private val initHooks = arrayOf(GetApplication, ModuleEntry)

        fun initHooks() {
            for (h in initHooks) {
                if (h.isInited) continue
                try {
                    if (HookInit.processName == HookInit.packageName) {
                        h.init()
                        h.isInited = true
                    }
                } catch (thr: Throwable) {
                    Log.t(thr)
                }
            }
        }
    }
}