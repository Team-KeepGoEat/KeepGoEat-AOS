package org.keepgoeat.util.mixpanel

import org.keepgoeat.presentation.model.MixPanelEvent
import org.keepgoeat.presentation.type.LoginPlatformType

object SignEvent {
    fun completeSignUp(platform: LoginPlatformType) =
        MixPanelEvent("Sign Up", mapOf("Platform" to platform.label))

    fun completeLogin() = MixPanelEvent("Login", null)
}
