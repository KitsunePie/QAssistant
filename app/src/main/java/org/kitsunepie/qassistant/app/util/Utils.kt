package org.kitsunepie.qassistant.app.util

import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.net.Uri
import com.github.kyuubiran.ezxhelper.init.InitFields.appContext
import com.github.kyuubiran.ezxhelper.init.InitFields.moduleRes
import org.kitsunepie.qassistant.R

object Utils {
    /**
     * 打开链接
     * @param url 链接
     */
    fun Context.openUrl(url: String) {
        val uri = Uri.parse(url)
        val intent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(intent)
    }

    val resources: Resources by lazy {
        try {
            moduleRes.getString(R.string.app_name)
            moduleRes
        } catch (thr: Throwable) {
            appContext.resources
        }
    }

    var isPreview = false
}