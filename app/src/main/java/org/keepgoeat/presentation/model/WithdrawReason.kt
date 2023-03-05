package org.keepgoeat.presentation.model

import org.keepgoeat.presentation.type.WithdrawCheckType

data class WithdrawReason(
    val reason: String,
    var isClicked: WithdrawCheckType,
)
