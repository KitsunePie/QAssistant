package org.kitsunepie.qassistant.app.ui.module.fragment.page

import com.github.kyuubiran.ezxhelper.init.InitFields.moduleRes
import org.kitsunepie.maitungtmui.base.uiScreen
import org.kitsunepie.maitungtmui.base.uiSwitchItem
import org.kitsunepie.maitungtmui.fragment.ViewMap
import org.kitsunepie.qassistant.R
import org.kitsunepie.qassistant.app.hook.normal.simplify.HideRedDot
import org.kitsunepie.qassistant.app.hook.normal.simplify.PreventDiyCardLoad
import org.kitsunepie.qassistant.app.hook.normal.simplify.PreventQBossAdLoad

object PurifySettingPage {
    val page: ViewMap = listOf(
        uiScreen {
            name = moduleRes.getString(R.string.module_setting_purify_main)
            contains = linkedMapOf(
                uiSwitchItem {
                    title = PreventQBossAdLoad.name
                    summary = PreventQBossAdLoad.desc
                }
            )
        },
        uiScreen {
            name = moduleRes.getString(R.string.module_setting_purify_extension)
            contains = linkedMapOf(
                uiSwitchItem {
                    title = HideRedDot.name
                    summary = HideRedDot.desc
                },
                uiSwitchItem {
                    title = PreventDiyCardLoad.name
                    summary = PreventDiyCardLoad.desc
                },
            )
        }
    )
}