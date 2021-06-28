package org.kitsunepie.qassistant.app.hook.base

import android.content.Context
import androidx.lifecycle.MutableLiveData
import org.kitsunepie.maitungtmui.base.UiSwitchPreference

abstract class BaseSwitchHook : BaseNormalHook, UiSwitchPreference {
    override var summary: String? = null
    override var enable: Boolean = true
    override var isInit: Boolean = false
    override val value: MutableLiveData<Boolean> = MutableLiveData<Boolean>().apply {
        value = isActivated()
        observeForever {
            setActivated(it)
        }
    }
    override var onClickListener: (Context) -> Boolean = { true }
}