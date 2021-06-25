package org.kitsunepie.qassistant.app.ui.module

import android.os.Bundle
import com.github.kyuubiran.ezxhelper.utils.parasitics.TransferActivity
import org.kitsunepie.qassistant.R
import org.kitsunepie.qassistant.databinding.ActivityMainBinding


class ModuleActivity : TransferActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Platform_MaterialComponents_Light)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}