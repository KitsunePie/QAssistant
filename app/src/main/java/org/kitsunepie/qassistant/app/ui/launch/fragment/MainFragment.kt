package org.kitsunepie.qassistant.app.ui.launch.fragment

import org.kitsunepie.maitungtmui.base.*
import org.kitsunepie.qassistant.R
import org.kitsunepie.qassistant.app.ui.module.fragment.mainSettingFragment
import org.kitsunepie.qassistant.app.util.Utils.resources

val mainFragment: UiScreen = uiScreen {
    name = resources.getString(R.string.app_name)
    summary = null
    contains = linkedMapOf(
        uiCategory {
            noTitle = true
            contains = linkedMapOf(
                uiClickableItem {
                    title = resources.getString(R.string.main_activity_function_preview)
                    summary = resources.getString(R.string.main_activity_function_preview_summary)
                    onClickListener = ClickToNewSetting(mainSettingFragment)
                })
        }
    )
}.second