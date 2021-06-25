package org.kitsunepie.qassistant.app.util

import android.content.Context
import android.content.Intent
import android.net.Uri

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
}