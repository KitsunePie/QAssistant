package org.kitsunepie.qassistant.app.ui.module.activity

import android.os.Bundle
import androidx.fragment.app.Fragment
import org.kitsunepie.maitungtmui.base.TitleAble
import org.kitsunepie.maitungtmui.fragment.MaiTungTMSettingFragment
import org.kitsunepie.qassistant.R
import org.kitsunepie.qassistant.app.ui.module.fragment.MainSettingFragment
import org.kitsunepie.qassistant.core.transfer.TransferMaiTungActivity

class ModuleActivity<T> : TransferMaiTungActivity<T>() where T : Fragment, T : TitleAble {
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_MaiTungTMUI)
        super.onCreate(savedInstanceState)
    }
    @Suppress("UNCHECKED_CAST")
    override val fragment: T = MaiTungTMSettingFragment().setUiScreen(MainSettingFragment) as T
}