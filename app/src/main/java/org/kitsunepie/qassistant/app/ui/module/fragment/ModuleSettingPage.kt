package org.kitsunepie.qassistant.app.ui.module.fragment

import com.github.kyuubiran.ezxhelper.init.InitFields.moduleRes
import org.kitsunepie.maitungtmui.base.UiScreen
import org.kitsunepie.maitungtmui.base.uiCategory
import org.kitsunepie.maitungtmui.base.uiScreen
import org.kitsunepie.maitungtmui.base.uiSwitchItem
import org.kitsunepie.qassistant.R
import org.kitsunepie.qassistant.core.config.Config
import org.kitsunepie.qassistant.core.config.ModuleConfig

val moduleSettingFragment: UiScreen = uiScreen {
    name = moduleRes.getString(R.string.module_other_setting_module)
    summary = null
    contains = linkedMapOf(
        uiCategory {
            name = moduleRes.getString(R.string.module_other_setting_module_startup)
            contains = linkedMapOf(
                uiSwitchItem {
                    title = moduleRes.getString(R.string.module_other_setting_module_startup_toast)
                    value.value = Config.sModulePref.getBoolean(ModuleConfig.M_STARTUP_TOAST, false)
                    value.observeForever {
                        Config.sModulePref.putBoolean(ModuleConfig.M_STARTUP_TOAST, it)
                    }
                }
            )
        }
    )
}.second