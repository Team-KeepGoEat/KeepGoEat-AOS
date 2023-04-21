package org.keepgoeat.presentation.common

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
