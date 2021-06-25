package org.kitsunepie.qassistant.app.ui.module.fragment

import com.github.kyuubiran.ezxhelper.init.InitFields.moduleRes
import com.github.kyuubiran.ezxhelper.utils.Log
import org.kitsunepie.maitungtmui.base.*
import org.kitsunepie.qassistant.R
import org.kitsunepie.qassistant.app.ui.module.fragment.page.purifySettingPage

val mainSettingFragment: UiScreen = uiScreen {
    name = moduleRes.getString(R.string.app_name)
    summary = null
    contains = linkedMapOf(
        uiCategory {
            name = moduleRes.getString(R.string.module_setting_title)
            contains = linkedMapOf(
                uiClickableItem {
                    title = moduleRes.getString(R.string.module_setting_purify)
                    onClickListener = ClickToNewPages(purifySettingPage)
                },
                uiClickableItem {
                    title = moduleRes.getString(R.string.module_setting_enhancement)
                    onClickListener = ClickToNewSetting(enhancementSettingFragment)
                },
                uiClickableItem {
                    title = moduleRes.getString(R.string.module_setting_assistant)
                    onClickListener = {
                        Log.toast(moduleRes.getString(R.string.nothing_here))
                        true
                    }
                },
                uiClickableItem {
                    title = moduleRes.getString(R.string.module_setting_other)
                    onClickListener = {
                        Log.toast(moduleRes.getString(R.string.nothing_here))
                        true
                    }
                }
            )
        },
        uiCategory {
            name = moduleRes.getString(R.string.module_other_setting_title)
            contains = linkedMapOf(
                uiClickableItem {
                    title = moduleRes.getString(R.string.module_other_setting_module)
                    onClickListener = ClickToNewSetting(moduleSettingFragment)
                },
                uiClickableItem {
                    title = moduleRes.getString(R.string.module_other_setting_fault_finding)
                    onClickListener = {
                        Log.toast(moduleRes.getString(R.string.nothing_here))
                        true
                    }
                },
                uiClickableItem {
                    title = moduleRes.getString(R.string.module_more_setting_about)
                    onClickListener = ClickToNewSetting(aboutFragment)
                }
            )
        },
    )
}.second