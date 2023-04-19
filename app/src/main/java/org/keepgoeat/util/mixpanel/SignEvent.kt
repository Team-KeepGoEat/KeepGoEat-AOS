package org.keepgoeat.util.mixpanel

import org.keepgoeat.presentation.model.MixPanelEvent
import org.keepgoeat.presentation.type.LoginPlatformType

object SignEvent {
    fun completeLogin() = MixPanelEvent("Login", null)

    fun deleteAccount(reasons: Map<String, Any>?) = MixPanelEvent("Delete Account", reasons)
}
