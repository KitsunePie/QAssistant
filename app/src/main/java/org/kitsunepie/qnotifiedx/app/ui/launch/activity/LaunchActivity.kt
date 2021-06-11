package org.kitsunepie.qnotifiedx.app.ui.launch.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.kitsunepie.qnotifiedx.R
import org.kitsunepie.qnotifiedx.databinding.ActivityMainBinding


class LaunchActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Platform_MaterialComponents_Light)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}