package org.kitsunepie.qassistant.app.ui.module.fragment

import com.github.kyuubiran.ezxhelper.utils.Log
import org.kitsunepie.maitungtmui.base.*
import org.kitsunepie.qassistant.R
import org.kitsunepie.qassistant.app.ui.module.activity.safeResources
import org.kitsunepie.qassistant.app.ui.module.fragment.page.purifySettingPage

val mainSettingFragment: UiScreen = uiScreen {
    name = safeResources.getString(R.string.app_name)
    summary = null
    contains = linkedMapOf(
        uiCategory {
            name = safeResources.getString(R.string.module_function_setting_title)
            contains = linkedMapOf(
                uiClickableItem {
                    title = safeResources.getString(R.string.module_function_setting_purify)
                    onClickListener = ClickToNewPages(purifySettingPage)
                },
                uiClickableItem {
                    title = safeResources.getString(R.string.module_function_setting_enhancement)
                    onClickListener = ClickToNewSetting(enhancementSettingFragment)
                },
                uiClickableItem {
                    title = safeResources.getString(R.string.module_function_setting_assistant)
                    onClickListener = {
                        Log.toast(safeResources.getString(R.string.nothing_here))
                        true
                    }
                },
                uiClickableItem {
                    title = safeResources.getString(R.string.module_function_setting_other)
                    onClickListener = {
                        Log.toast(safeResources.getString(R.string.nothing_here))
                        true
                    }
                }
            )
        },
        uiCategory {
            name = safeResources.getString(R.string.module_other_setting_title)
            contains = linkedMapOf(
                uiClickableItem {
                    title = safeResources.getString(R.string.module_other_setting_module)
                    onClickListener = ClickToNewSetting(moduleSettingFragment)
                },
                uiClickableItem {
                    title = safeResources.getString(R.string.module_other_setting_fault_finding)
                    onClickListener = {
                        Log.toast(safeResources.getString(R.string.nothing_here))
                        true
                    }
                },
            )
        },
        uiCategory {
            name = safeResources.getString(R.string.module_more_setting_title)
            contains = linkedMapOf(
                uiClickableItem {
                    title = safeResources.getString(R.string.module_more_setting_gugugu)
                },
                uiClickableItem {
                    title = safeResources.getString(R.string.module_more_setting_about)
                    onClickListener = ClickToNewSetting(aboutFragment)
                }
            )
        }
    )
}.second