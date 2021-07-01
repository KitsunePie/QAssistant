package org.kitsunepie.qassistant.app.ui.module.fragment

import org.kitsunepie.maitungtmui.base.UiScreen
import org.kitsunepie.maitungtmui.base.uiCategory
import org.kitsunepie.maitungtmui.base.uiScreen
import org.kitsunepie.qassistant.R
import org.kitsunepie.qassistant.app.hook.normal.function.PreventMessageRevoke
import org.kitsunepie.qassistant.app.util.Utils.resources

val enhancementSettingFragment: UiScreen = uiScreen {
    name = resources.getString(R.string.module_function_setting_enhancement)
    summary = null
    contains = linkedMapOf(
        uiCategory {
            name = resources.getString(R.string.module_function_setting_enhancement_chat)
            contains = linkedMapOf(
                PreventMessageRevoke.title to PreventMessageRevoke
            )
        }
    )
}.second