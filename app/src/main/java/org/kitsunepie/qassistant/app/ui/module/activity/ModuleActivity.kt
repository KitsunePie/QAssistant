package org.kitsunepie.qassistant.app.ui.module.activity

import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.github.kyuubiran.ezxhelper.init.EzXHelperInit.initAppContext
import com.github.kyuubiran.ezxhelper.init.InitFields.isAppContextInitialized
import com.github.kyuubiran.ezxhelper.init.InitFields.isModuleResInitialized
import com.github.kyuubiran.ezxhelper.utils.showToast
import org.kitsunepie.maitungtmui.base.TitleAble
import org.kitsunepie.maitungtmui.fragment.MaiTungTMSettingFragment
import org.kitsunepie.qassistant.R
import org.kitsunepie.qassistant.app.ui.module.fragment.mainSettingFragment
import org.kitsunepie.qassistant.core.transfer.TransferMaiTungActivity

class ModuleActivity<T> : TransferMaiTungActivity<T>() where T : Fragment, T : TitleAble {
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_MaiTungTMUI)
        if (!isAppContextInitialized) {
            if (!isModuleResInitialized) {
                showToast(getString(R.string.resourcesFallback), Toast.LENGTH_LONG)
            }
            initAppContext(applicationContext, initResources = !isModuleResInitialized)
        }
        super.onCreate(savedInstanceState)
    }

    @Suppress("UNCHECKED_CAST")
    override val fragment: T by lazy {
        MaiTungTMSettingFragment().setUiScreen(mainSettingFragment) as T
    }
}