package org.kitsunepie.qassistant.app.ui.launch.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.kitsunepie.qassistant.R
import org.kitsunepie.qassistant.databinding.ActivityMainBinding


class LaunchActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Platform_MaterialComponents_Light)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}