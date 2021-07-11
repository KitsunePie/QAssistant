package org.kitsunepie.qassistant.app.hook.normal.simplify

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.github.kyuubiran.ezxhelper.utils.findMethodByCondition
import com.github.kyuubiran.ezxhelper.utils.putObject
import org.kitsunepie.qassistant.annotations.NormalHookEntry
import org.kitsunepie.qassistant.app.hook.base.BaseSwitchHook
import org.kitsunepie.qassistant.app.util.hookAfter

@NormalHookEntry
object HideRedDot : BaseSwitchHook() {
    override var title: String = "隐藏小红点"
    override var summary: String? = "隐藏各个界面中的大部分小红点"

    override val needReboot: Boolean = true

    private val TRANSPARENT_PNG = byteArrayOf(
        0x89.toByte(), 0x50.toByte(), 0x4E.toByte(), 0x47.toByte(),
        0x0D.toByte(), 0x0A.toByte(), 0x1A.toByte(), 0x0A.toByte(),
        0x00.toByte(), 0x00.toByte(), 0x00.toByte(), 0x0D.toByte(),
        0x49.toByte(), 0x48.toByte(), 0x44.toByte(), 0x52.toByte(),
        0x00.toByte(), 0x00.toByte(), 0x00.toByte(), 0x01.toByte(),
        0x00.toByte(), 0x00.toByte(), 0x00.toByte(), 0x01.toByte(),
        0x08.toByte(), 0x06.toByte(), 0x00.toByte(), 0x00.toByte(),
        0x00.toByte(), 0x1F.toByte(), 0x15.toByte(), 0xC4.toByte(),
        0x89.toByte(), 0x00.toByte(), 0x00.toByte(), 0x00.toByte(),
        0x0B.toByte(), 0x49.toByte(), 0x44.toByte(), 0x41.toByte(),
        0x54.toByte(), 0x08.toByte(), 0xD7.toByte(), 0x63.toByte(),
        0x60.toByte(), 0x00.toByte(), 0x02.toByte(), 0x00.toByte(),
        0x00.toByte(), 0x05.toByte(), 0x00.toByte(), 0x01.toByte(),
        0xE2.toByte(), 0x26.toByte(), 0x05.toByte(), 0x9B.toByte(),
        0x00.toByte(), 0x00.toByte(), 0x00.toByte(), 0x00.toByte(),
        0x49.toByte(), 0x45.toByte(), 0x4E.toByte(), 0x44.toByte(),
        0xAE.toByte(), 0x42.toByte(), 0x60.toByte(), 0x82.toByte()
    )

    override fun init() {
        findMethodByCondition("com.tencent.theme.ResourcesFactory") {
            (it.name == "createImageFromResourceStream" || it.name == "a") && it.parameterTypes.size == 7
        }.hookAfter(this) { param ->
            if (!param.args[3].toString().contains("skin_tips_dot")) return@hookAfter
            param.result.putObject(
                "a",
                BitmapFactory.decodeByteArray(TRANSPARENT_PNG, 0, TRANSPARENT_PNG.size),
                Bitmap::class.java
            )
        }
    }
}