package org.kitsunepie.qassistant.app.ui.module.activity

import android.content.res.Resources
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.github.kyuubiran.ezxhelper.init.EzXHelperInit.initAppContext
import com.github.kyuubiran.ezxhelper.init.InitFields
import com.github.kyuubiran.ezxhelper.init.InitFields.appContext
import org.kitsunepie.maitungtmui.base.TitleAble
import org.kitsunepie.maitungtmui.fragment.MaiTungTMSettingFragment
import org.kitsunepie.qassistant.R
import org.kitsunepie.qassistant.app.ui.module.fragment.mainSettingFragment
import org.kitsunepie.qassistant.core.transfer.TransferMaiTungActivity

class ModuleActivity<T> : TransferMaiTungActivity<T>() where T : Fragment, T : TitleAble {
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_MaiTungTMUI)
        tempResources = this.resources
        try {
            val ignore = appContext.packageName
            ignore.plus(Math.random())
        } catch (e: Exception) {
            e.printStackTrace()
            initAppContext(applicationContext)
        }
        super.onCreate(savedInstanceState)
    }

    @Suppress("UNCHECKED_CAST")
    override val fragment: T by lazy {
        MaiTungTMSettingFragment().setUiScreen(mainSettingFragment) as T
    }
}

private lateinit var tempResources: Resources

val safeResources: Resources by lazy {
    val aResources: Resources = try {
        InitFields.moduleRes
    } catch (e: Exception) {
        e.printStackTrace()
        tempResources
    }
    aResources
}