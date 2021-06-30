package org.kitsunepie.qassistant.app.ui.module.fragment

import org.kitsunepie.maitungtmui.base.UiScreen
import org.kitsunepie.maitungtmui.base.uiCategory
import org.kitsunepie.maitungtmui.base.uiScreen
import org.kitsunepie.maitungtmui.base.uiSwitchItem
import org.kitsunepie.qassistant.R
import org.kitsunepie.qassistant.app.ui.module.activity.safeResources
import org.kitsunepie.qassistant.core.config.Config
import org.kitsunepie.qassistant.core.config.ModuleConfig

val moduleSettingFragment: UiScreen = uiScreen {
    name = safeResources.getString(R.string.module_other_setting_module)
    summary = null
    contains = linkedMapOf(
        uiCategory {
            name = safeResources.getString(R.string.module_other_setting_module_startup)
            contains = linkedMapOf(
                uiSwitchItem {
                    title =
                        safeResources.getString(R.string.module_other_setting_module_startup_toast)
                    value.value = Config.sModulePref.getBoolean(ModuleConfig.M_STARTUP_TOAST, false)
                    value.observeForever {
                        Config.sModulePref.putBoolean(ModuleConfig.M_STARTUP_TOAST, it)
                    }
                }
            )
        }
    )
}.second