package com.qnotifiedx.app.hook.base

import com.qnotifiedx.app.hook.moduleinit.GetApplication
import com.qnotifiedx.app.hook.moduleinit.ModuleEntry
import com.qnotifiedx.core.processctrl.ProcessInfo.isCurrentProc

/**
 * 模块初始化相关的Hook
 */
abstract class BaseModuleInit : BaseHook() {

    companion object {
        private val initHooks = arrayOf(GetApplication, ModuleEntry)

        fun initHooks() {
            for (h in initHooks) {
                if (!h.inited) {
                    for (proc in h.targetProc) {
                        if (proc.isCurrentProc) {
                            h.init()
                            h.inited = true
                        }
                    }
                }
            }
        }
    }
}