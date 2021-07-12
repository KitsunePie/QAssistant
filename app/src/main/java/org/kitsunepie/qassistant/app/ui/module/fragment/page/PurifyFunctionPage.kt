package org.kitsunepie.qassistant.app.ui.module.fragment.page

import com.github.kyuubiran.ezxhelper.init.InitFields.moduleRes
import org.kitsunepie.maitungtmui.base.uiCategory
import org.kitsunepie.maitungtmui.base.uiScreen
import org.kitsunepie.maitungtmui.fragment.ViewMap
import org.kitsunepie.qassistant.R
import org.kitsunepie.qassistant.app.hook.normal.simplify.*

val purifyFunctionPage: ViewMap = listOf(
    uiScreen {
        name = moduleRes.getString(R.string.module_function_setting_purify_main)
        contains = linkedMapOf(
            uiCategory {
                name = moduleRes.getString(R.string.module_function_setting_purify_main_top)
                contains = linkedMapOf(
                    PreventQBossAdLoad.v,
                    HideCameraButton.v,
                )
            }
        )
    },
    uiScreen {
        name = moduleRes.getString(R.string.module_function_setting_purify_side)
        contains = linkedMapOf(
        )
    },
    uiScreen {
        name = moduleRes.getString(R.string.module_function_setting_purify_chat)
        contains = linkedMapOf(
        )
    },
    uiScreen {
        name = moduleRes.getString(R.string.module_function_setting_purify_group)
        contains = linkedMapOf(
            uiCategory {
                name = moduleRes.getString(R.string.module_function_setting_purify_group_other)
                contains = linkedMapOf(RemoveGroupApp.v)
            }
        )
    },
    uiScreen {
        name = moduleRes.getString(R.string.module_function_setting_purify_extension)
        contains = linkedMapOf(
            uiCategory {
                name =
                    moduleRes.getString(R.string.module_function_setting_purify_extension_prevent_load)
                contains = linkedMapOf(
                    HideRedDot.v,
                    PreventDiyCardLoad.v,
                )
            }
        )
    }
)