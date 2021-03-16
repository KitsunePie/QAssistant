package com.qnotifiedx.app.hook.normal

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.qnotifiedx.annotations.NormalHookEntry
import com.qnotifiedx.app.hook.base.BaseNormalHook
import com.qnotifiedx.app.util.findMethodByCondition
import com.qnotifiedx.app.util.hookAfter
import com.qnotifiedx.app.util.putObject
import com.qnotifiedx.core.processctrl.Process

@NormalHookEntry
object HideRedDot : BaseNormalHook() {
    override val name: String = "隐藏小红点"
    override val desc: String = "隐藏各个界面中的大部分小红点"

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
        }.also { m ->
            m.hookAfter {
                if (!enable) return@hookAfter
                if (!it.args[3].toString().contains("skin_tips_dot")) return@hookAfter
                it.result.putObject(
                    "a",
                    BitmapFactory.decodeByteArray(TRANSPARENT_PNG, 0, TRANSPARENT_PNG.size),
                    Bitmap::class.java
                )
            }
        }
    }
}