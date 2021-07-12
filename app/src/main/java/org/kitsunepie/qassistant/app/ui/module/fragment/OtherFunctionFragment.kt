package org.kitsunepie.qassistant.app.ui.module.fragment

import com.github.kyuubiran.ezxhelper.init.InitFields.moduleRes
import org.kitsunepie.maitungtmui.base.UiScreen
import org.kitsunepie.maitungtmui.base.uiCategory
import org.kitsunepie.maitungtmui.base.uiScreen
import org.kitsunepie.qassistant.R
import org.kitsunepie.qassistant.app.hook.normal.function.PreventUpdate

val otherFunctionFragment: UiScreen = uiScreen {
    name = moduleRes.getString(R.string.module_function_setting_other)
    summary = null
    contains = linkedMapOf(
        uiCategory {
            noTitle = true
            contains = linkedMapOf(
                PreventUpdate.title to PreventUpdate
            )
        }
    )
}.second