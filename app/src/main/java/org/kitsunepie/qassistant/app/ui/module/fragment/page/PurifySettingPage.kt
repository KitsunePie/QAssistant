package org.kitsunepie.qassistant.app.ui.module.fragment.page

import org.kitsunepie.maitungtmui.base.uiCategory
import org.kitsunepie.maitungtmui.base.uiScreen
import org.kitsunepie.maitungtmui.fragment.ViewMap
import org.kitsunepie.qassistant.R
import org.kitsunepie.qassistant.app.hook.normal.simplify.HideRedDot
import org.kitsunepie.qassistant.app.hook.normal.simplify.PreventDiyCardLoad
import org.kitsunepie.qassistant.app.hook.normal.simplify.PreventQBossAdLoad
import org.kitsunepie.qassistant.app.hook.normal.simplify.RemoveGroupApp
import org.kitsunepie.qassistant.app.util.Utils.resources

val purifySettingPage: ViewMap = listOf(
    uiScreen {
        name = resources.getString(R.string.module_function_setting_purify_main)
        contains = linkedMapOf(
            uiCategory {
                name = resources.getString(R.string.module_function_setting_purify_main_top)
                contains = linkedMapOf(PreventQBossAdLoad.title to PreventQBossAdLoad)
            }
        )
    },
    uiScreen {
        name = resources.getString(R.string.module_function_setting_purify_side)
        contains = linkedMapOf(
            uiCategory {
                name = resources.getString(R.string.module_function_setting_purify_main_top)
                contains = linkedMapOf(PreventQBossAdLoad.title to PreventQBossAdLoad)
            }
        )
    },
    uiScreen {
        name = resources.getString(R.string.module_function_setting_purify_chat)
        contains = linkedMapOf(
        )
    },
    uiScreen {
        name = resources.getString(R.string.module_function_setting_purify_group)
        contains = linkedMapOf(
            uiCategory {
                name = resources.getString(R.string.module_function_setting_purify_group_other)
                contains = linkedMapOf(RemoveGroupApp.title to RemoveGroupApp)
            }
        )
    },
    uiScreen {
        name = resources.getString(R.string.module_function_setting_purify_extension)
        contains = linkedMapOf(
            uiCategory {
                name =
                    resources.getString(R.string.module_function_setting_purify_extension_prevent_load)
                contains = linkedMapOf(
                    HideRedDot.title to HideRedDot,
                    PreventDiyCardLoad.title to PreventDiyCardLoad,
                )
            }
        )
    }
)