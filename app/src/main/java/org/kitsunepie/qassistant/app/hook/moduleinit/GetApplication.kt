package org.kitsunepie.qassistant.app.hook.moduleinit

import android.app.Application
import com.github.kyuubiran.ezxhelper.init.EzXHelperInit
import com.github.kyuubiran.ezxhelper.init.InitFields.appContext
import com.github.kyuubiran.ezxhelper.utils.Log
import com.github.kyuubiran.ezxhelper.utils.getFieldBySig
import com.github.kyuubiran.ezxhelper.utils.getMethodBySig
import com.github.kyuubiran.ezxhelper.utils.getStaticNonNullAs
import de.robv.android.xposed.callbacks.XCallback
import org.kitsunepie.qassistant.BuildConfig
import org.kitsunepie.qassistant.R
import org.kitsunepie.qassistant.app.HookLoader
import org.kitsunepie.qassistant.app.hook.base.BaseHook
import org.kitsunepie.qassistant.app.hook.base.HookInitializer
import org.kitsunepie.qassistant.app.util.hookAfter
import org.kitsunepie.qassistant.core.config.Config
import org.kitsunepie.qassistant.core.config.ModuleConfig

object GetApplication : BaseHook {
    override fun isActivated(): Boolean {
        return true
    }

    override var isInited: Boolean = false

    override fun init() {
        getMethodBySig("Lcom/tencent/mobileqq/startup/step/LoadDex;->doStep()Z").also { m ->
            m.hookAfter(this, XCallback.PRIORITY_HIGHEST) {
                //获取Context
                val context =
                    getFieldBySig("Lcom/tencent/common/app/BaseApplicationImpl;->sApplication:Lcom/tencent/common/app/BaseApplicationImpl;")
                        .getStaticNonNullAs<Application>()
                //设置全局Context
                EzXHelperInit.initAppContext(context, true)
                EzXHelperInit.initActivityProxyManager(
                    BuildConfig.APPLICATION_ID,
                    "com.tencent.mobileqq.activity.photo.CameraPreviewActivity",
                    HookLoader::class.java.classLoader!!
                )
                EzXHelperInit.initSubActivity()
                if (!Config.sModulePref.getBoolean(ModuleConfig.M_STARTUP_TOAST, false)) {
                    Log.toast(appContext.resources.getString(R.string.load_successful))
                }
                //加载普通Hook
                HookInitializer.initNormalHooks()
            }
        }
    }
}