package org.keepgoeat.util.mixpanel

import org.keepgoeat.presentation.model.MixPanelEvent

object SignEvent {
    fun completeSignUp(platForm: String) = MixPanelEvent("Sign Up", mapOf("Platform" to platForm))

    fun completeLogin() = MixPanelEvent("Login", null)

    fun deleteAccount(reasons: Map<String, Any>?) = MixPanelEvent("Delete Account", reasons)
}
