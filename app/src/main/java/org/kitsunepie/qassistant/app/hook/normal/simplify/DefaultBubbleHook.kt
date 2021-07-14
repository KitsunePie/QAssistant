package org.kitsunepie.qassistant.app.hook.normal.simplify

import com.github.kyuubiran.ezxhelper.init.InitFields
import com.github.kyuubiran.ezxhelper.init.InitFields.appContext
import com.github.kyuubiran.ezxhelper.utils.Log
import com.github.kyuubiran.ezxhelper.utils.showToast
import org.kitsunepie.qassistant.R
import org.kitsunepie.qassistant.annotations.NormalHookEntry
import org.kitsunepie.qassistant.app.hook.base.BaseSwitchHook
import java.io.File

@NormalHookEntry
object DefaultBubbleHook : BaseSwitchHook() {

    override fun init() {
        Unit
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

    override val title: String = "隐藏个性气泡"
}
