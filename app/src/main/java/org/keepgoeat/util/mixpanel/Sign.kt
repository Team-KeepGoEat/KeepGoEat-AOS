package org.keepgoeat.util.mixpanel

import org.keepgoeat.presentation.model.MixPanelEvent
import org.keepgoeat.presentation.type.LoginPlatformType

fun MixPanelEvents.Sign.Companion.completeSignUp(platform: LoginPlatformType) =
    MixPanelEvent(SIGN_UP, mapOf("Platform" to platform.label))

fun MixPanelEvents.Sign.Companion.completeLogin() =
    MixPanelEvent(LOGIN, null)
