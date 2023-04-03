package org.keepgoeat.util.mixpanel

import org.keepgoeat.presentation.model.MixPanelEvent
import org.keepgoeat.presentation.type.SocialLoginType

fun MixPanelEvents.Sign.Companion.completeSignUp(socialLoginType: SocialLoginType) =
    MixPanelEvent(SIGN_UP, mapOf("Platform" to socialLoginType.label))

fun MixPanelEvents.Sign.Companion.completeLogin() =
    MixPanelEvent(LOGIN, null)
