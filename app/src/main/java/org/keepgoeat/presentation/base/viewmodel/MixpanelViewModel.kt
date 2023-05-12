package org.keepgoeat.presentation.base.viewmodel

import org.keepgoeat.util.mixpanel.MixpanelProvider
import javax.inject.Inject

abstract class MixpanelViewModel : BaseViewModel() {
    @Inject
    lateinit var mixpanelProvider: MixpanelProvider

    fun startRecodingScreenTime() {
        mixpanelProvider.startRecodingScreenTime()
    }

    fun stopRecodingScreenTime(screenName: String) {
        mixpanelProvider.stopRecodingScreenTime(screenName)
    }
}
