package com.qnotifiedx.app.ui.common.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.qnotifiedx.app.R

class CommonActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Platform_MaterialComponents_Light)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}