package org.keepgoeat.util.mixpanel

sealed class MixPanelEvents {
    class Sign : MixPanelEvents() {
        companion object {
            const val SIGN_UP = "Sign Up"
            const val LOGIN = "Login"
        }
    }

    class Goal : MixPanelEvents() {
        companion object
    }

    class Screen : MixPanelEvents() {
        companion object
    }
}
