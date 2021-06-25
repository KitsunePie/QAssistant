package org.kitsunepie.qassistant.app.ui.module.fragment

import com.github.kyuubiran.ezxhelper.init.InitFields.moduleRes
import org.kitsunepie.maitungtmui.base.*
import org.kitsunepie.qassistant.R

object MainSettingFragment : UiScreen {
    override var name: String = moduleRes.getString(R.string.app_name)
    override var summary: String? = null
    override var contains: UiMap = linkedMapOf(
        uiCategory {
            name = moduleRes.getString(R.string.module_setting_title)
            contains = linkedMapOf(
                uiClickableItem {
                    title = moduleRes.getString(R.string.module_setting_purify)
                },
                uiClickableItem {
                    title = moduleRes.getString(R.string.module_setting_extension)
                },
                uiClickableItem {
                    title = moduleRes.getString(R.string.module_setting_assistant)

                },
                uiClickableItem {
                    title = moduleRes.getString(R.string.module_setting_other)
                }
            )
        },
        uiCategory {
            name =  moduleRes.getString(R.string.module_other_setting_title)
            contains = linkedMapOf(
                uiClickableItem {
                    title = moduleRes.getString(R.string.module_other_setting_args)
                },
                uiClickableItem {
                    title = moduleRes.getString(R.string.module_other_setting_fault_finding)
                },
                uiClickableItem {
                    title = moduleRes.getString(R.string.module_more_setting_about)
                }
            )
        },
    )
}