package org.kitsunepie.qnotifiedx.app.hook.moduleinit

import android.app.Application
import com.github.kyuubiran.ezxhelper.init.EzXHelperInit
import com.github.kyuubiran.ezxhelper.init.InitFields.appContext
import com.github.kyuubiran.ezxhelper.utils.Log
import com.github.kyuubiran.ezxhelper.utils.getFieldBySig
import com.github.kyuubiran.ezxhelper.utils.getMethodBySig
import com.github.kyuubiran.ezxhelper.utils.getStaticNonNullAs
import de.robv.android.xposed.callbacks.XCallback
import org.kitsunepie.qnotifiedx.BuildConfig
import org.kitsunepie.qnotifiedx.R
import org.kitsunepie.qnotifiedx.app.HookLoader
import org.kitsunepie.qnotifiedx.app.hook.base.BaseModuleInitHook
import org.kitsunepie.qnotifiedx.app.hook.base.BaseNormalHook
import org.kitsunepie.qnotifiedx.app.util.MMKVInit
import org.kitsunepie.qnotifiedx.app.util.hookAfter

object GetApplication : BaseModuleInitHook() {
    override val name: String = "获取全局Context"
    override var isEnabled: Boolean = true

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
                Log.toast(appContext.resources.getString(R.string.load_successful))
                MMKVInit.init()
                //加载普通Hook
                BaseNormalHook.initHooks()
            }
        }
    }
}