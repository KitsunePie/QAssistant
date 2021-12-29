package org.kitsunepie.qassistant.app.hook.normal.simplify

import com.github.kyuubiran.ezxhelper.init.InitFields
import com.github.kyuubiran.ezxhelper.init.InitFields.appContext
import com.github.kyuubiran.ezxhelper.utils.Log
import com.github.kyuubiran.ezxhelper.utils.showToast
import org.kitsunepie.qassistant.R
import org.kitsunepie.qassistant.annotations.NormalHookEntry
import org.kitsunepie.qassistant.app.hook.base.BaseHook
import org.kitsunepie.qassistant.app.hook.base.IHookInfo
import java.io.File

@NormalHookEntry
object DefaultBubbleHook : BaseHook(), IHookInfo {

    override fun init() {
    }

    override fun isActivated(): Boolean {
        val dir = File(appContext.filesDir.absolutePath + "/bubble_info")
        return !dir.exists() || !dir.canRead()
    }

    override fun setActivated(value: Boolean) {
        try {
            val dir = File(appContext.filesDir.absolutePath + "/bubble_info")
            val curr = !dir.exists() || !dir.canRead()
            // 开启需要重启QQ，关闭即时生效
            if (value && !curr) Log.toast(InitFields.moduleRes.getString(R.string.reboot_to_effect))

            if (dir.exists()) {
                if (value && !curr) {
                    dir.setWritable(false)
                    dir.setReadable(false)
                    dir.setExecutable(false)
                }
                if (!value && curr) {
                    dir.setWritable(true)
                    dir.setReadable(true)
                    dir.setExecutable(true)
                }
            }
        } catch (e: Exception) {
            Log.e(e)
            appContext.showToast("出错力：$e")
        }
    }

    override val title: String
        get() = "默认气泡"

    override val summary: String
        get() = "那么多花里胡哨的，你礼貌吗？"
}
