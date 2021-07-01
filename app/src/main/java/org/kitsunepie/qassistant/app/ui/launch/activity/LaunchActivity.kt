package org.kitsunepie.qassistant.app.ui.launch.activity

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.github.kyuubiran.ezxhelper.init.EzXHelperInit.initAppContext
import com.github.kyuubiran.ezxhelper.init.EzXHelperInit.setLogTag
import com.github.kyuubiran.ezxhelper.init.EzXHelperInit.setToastTag
import org.kitsunepie.maitungtmui.activity.MaiTungTMStyleActivity
import org.kitsunepie.maitungtmui.base.TitleAble
import org.kitsunepie.maitungtmui.fragment.MaiTungTMSettingFragment
import org.kitsunepie.qassistant.R
import org.kitsunepie.qassistant.app.ui.launch.fragment.mainFragment
import org.kitsunepie.qassistant.app.util.Utils


class LaunchActivity<T> : MaiTungTMStyleActivity<T>() where T : Fragment, T : TitleAble {
    @Suppress("UNCHECKED_CAST")
    override val fragment: T by lazy {
        MaiTungTMSettingFragment().setUiScreen(mainFragment) as T
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_MaiTungTMUI)
        initAppContext(applicationContext)
        setLogTag("QAssistant")
        setToastTag("QAssistant")
        Utils.isPreview = true
        super.onCreate(savedInstanceState)
    }
}