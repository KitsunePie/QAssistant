package com.kitsunepie.qnotifiedx.app.ui.module.activity

import android.os.Bundle
import com.kitsunepie.qnotifiedx.core.resinjection.transferactivity.AppCompatTransferActivity
import com.qnotifiedx.app.R

class MainActivity : AppCompatTransferActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Platform_MaterialComponents_Light)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setTitle(R.string.title_activity_main)
    }
}