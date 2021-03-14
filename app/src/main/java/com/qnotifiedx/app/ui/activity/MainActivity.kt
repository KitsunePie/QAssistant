package com.qnotifiedx.app.ui.activity

import android.os.Bundle
import com.qnotifiedx.app.R
import com.qnotifiedx.app.ui.activity.base.AppCompatTransferActivity

class MainActivity : AppCompatTransferActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}