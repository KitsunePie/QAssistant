package com.qnotifiedx.app.ui.activity.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

open class AppCompatTransferActivity : AppCompatActivity() {
    override fun getClassLoader(): ClassLoader {
        return AppCompatTransferActivity::class.java.classLoader!!
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        val windowState = savedInstanceState.getBundle("android:viewHierarchyState")
        windowState?.let {
            it.classLoader = AppCompatTransferActivity::class.java.classLoader!!
        }
        super.onRestoreInstanceState(savedInstanceState)
    }
}