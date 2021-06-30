package org.kitsunepie.qassistant.core.transfer

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.github.kyuubiran.ezxhelper.utils.parasitics.ActivityProxyManager
import com.github.kyuubiran.ezxhelper.utils.parasitics.FixedClassLoader
import com.github.kyuubiran.ezxhelper.utils.parasitics.TransferActivity
import org.kitsunepie.maitungtmui.activity.MaiTungTMStyleActivity
import org.kitsunepie.maitungtmui.base.TitleAble

abstract class TransferMaiTungActivity<T> :
    MaiTungTMStyleActivity<T>() where T : Fragment, T : TitleAble {

    override fun getClassLoader(): ClassLoader {
        return try {
            FixedClassLoader(
                ActivityProxyManager.MODULE_CLASS_LOADER,
                ActivityProxyManager.HOST_CLASS_LOADER
            )
        } catch (e: Exception) {
            //e.printStackTrace()
            super.getClassLoader()
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        val windowState = savedInstanceState.getBundle("android:viewHierarchyState")
        windowState?.let {
            it.classLoader = TransferActivity::class.java.classLoader!!
        }
        super.onRestoreInstanceState(savedInstanceState)
    }
}