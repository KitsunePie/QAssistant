package org.kitsunepie.qassistant.app.ui.module.fragment

import com.github.kyuubiran.ezxhelper.init.InitFields.moduleRes
import org.kitsunepie.maitungtmui.base.uiCategory
import org.kitsunepie.maitungtmui.base.uiClickableItem
import org.kitsunepie.maitungtmui.base.uiScreen
import org.kitsunepie.qassistant.R
import org.kitsunepie.qassistant.app.util.Utils.openUrl

val aboutFragment = uiScreen {
    name = moduleRes.getString(R.string.module_more_setting_about_links)
    summary = null
    contains = linkedMapOf(
        uiCategory {
            name = moduleRes.getString(R.string.module_more_setting_about)
            contains = linkedMapOf(
                uiClickableItem {
                    title = moduleRes.getString(R.string.module_more_setting_about_goto_github)
                    summary =
                        moduleRes.getString(R.string.module_more_setting_about_goto_github_summary)
                    onClickListener = {
                        it.openUrl("https://github.com/KitsunePie/QAssistant")
                        true
                    }
                },
                uiClickableItem {
                    title =
                        moduleRes.getString(R.string.module_more_setting_about_join_telegram_channel)
                    summary =
                        moduleRes.getString(R.string.module_more_setting_about_join_telegram_channel_summary)
                    onClickListener = {
                        it.openUrl("https://t.me/QAssistant")
                        true
                    }
                }
            )
        }
    )
}.second