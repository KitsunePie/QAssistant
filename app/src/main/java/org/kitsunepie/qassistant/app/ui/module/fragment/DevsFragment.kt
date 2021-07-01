package org.kitsunepie.qassistant.app.ui.module.fragment

import org.kitsunepie.maitungtmui.base.UiScreen
import org.kitsunepie.maitungtmui.base.uiCategory
import org.kitsunepie.maitungtmui.base.uiClickableItem
import org.kitsunepie.maitungtmui.base.uiScreen
import org.kitsunepie.qassistant.R
import org.kitsunepie.qassistant.app.ui.module.activity.safeResources
import org.kitsunepie.qassistant.app.util.Utils.openUrl

val devsFragment: UiScreen = uiScreen {
    name = safeResources.getString(R.string.module_more_setting_about_main_devs)
    summary = null
    contains = linkedMapOf(
        uiCategory {
            noTitle = true
            contains = linkedMapOf(
                uiClickableItem {
                    title =
                        safeResources.getString(R.string.module_more_setting_about_main_devs_kyuubiran)
                    summary =
                        safeResources.getString(R.string.module_more_setting_about_main_devs_dev_summary)
                    onClickListener = {
                        it.openUrl("https://github.com/KyuubiRan")
                        true
                    }
                },
                uiClickableItem {
                    title =
                        safeResources.getString(R.string.module_more_setting_about_main_devs_ketal)
                    summary =
                        safeResources.getString(R.string.module_more_setting_about_main_devs_dev_summary)
                    onClickListener = {
                        it.openUrl("https://github.com/keta1")
                        true
                    }
                },
                uiClickableItem {
                    title =
                        safeResources.getString(R.string.module_more_setting_about_main_devs_singleneuron)
                    summary =
                        safeResources.getString(R.string.module_more_setting_about_main_devs_dev_summary)
                    onClickListener = {
                        it.openUrl("https://github.com/singleNeuron")
                        true
                    }
                },
                uiClickableItem {
                    title =
                        safeResources.getString(R.string.module_more_setting_about_main_devs_nextalone)
                    summary =
                        safeResources.getString(R.string.module_more_setting_about_main_devs_dev_summary)
                    onClickListener = {
                        it.openUrl("https://github.com/NextAlone")
                        true
                    }

                },
                uiClickableItem {
                    title =
                        safeResources.getString(R.string.module_more_setting_about_main_devs_maitung)
                    summary =
                        safeResources.getString(R.string.module_more_setting_about_main_devs_ui_summary)
                    onClickListener = {
                        it.openUrl("https://github.com/Lagrio")
                        true
                    }
                }
            )
        },
        uiCategory {
            noTitle = true
            summary = null
            contains = linkedMapOf(
                uiClickableItem {
                    title =
                        safeResources.getString(R.string.module_more_setting_about_main_devs_join_us)
                }
            )
        }
    )
}.second