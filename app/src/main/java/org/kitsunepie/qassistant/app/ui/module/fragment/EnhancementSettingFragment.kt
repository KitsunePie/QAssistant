package org.kitsunepie.qassistant.app.ui.module.fragment

import com.github.kyuubiran.ezxhelper.init.InitFields.moduleRes
import org.kitsunepie.maitungtmui.base.UiScreen
import org.kitsunepie.maitungtmui.base.uiCategory
import org.kitsunepie.maitungtmui.base.uiScreen
import org.kitsunepie.qassistant.R
import org.kitsunepie.qassistant.app.hook.normal.function.PreventMessageRevoke

val enhancementSettingFragment: UiScreen = uiScreen {
    name = moduleRes.getString(R.string.module_setting_enhancement)
    summary = null
    contains = linkedMapOf(
        uiCategory {
            name = moduleRes.getString(R.string.module_setting_enhancement_chat)
            contains = linkedMapOf(
                PreventMessageRevoke.title to PreventMessageRevoke
            )
        }
    )
}.second