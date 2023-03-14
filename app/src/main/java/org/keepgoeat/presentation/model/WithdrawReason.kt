package org.keepgoeat.presentation.model

import androidx.annotation.StringRes
import org.keepgoeat.R

enum class WithdrawReason(
    @StringRes val reason: Int,
) {
    REASON1(R.string.withdraw_reason1),
    REASON2(R.string.withdraw_reason2),
    REASON3(R.string.withdraw_reason3),
    REASON4(R.string.withdraw_reason4),
    REASON5(R.string.withdraw_other_reason_description),
}
