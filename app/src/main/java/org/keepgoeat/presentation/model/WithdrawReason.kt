package org.keepgoeat.presentation.model

import androidx.annotation.StringRes
import org.keepgoeat.R

enum class WithdrawReason( // TODO type 패키지로 이동
    @StringRes val reason: Int,
) {
    QUIT_ISSUE(R.string.withdraw_reason1),
    FREQUENCY_ISSUE(R.string.withdraw_reason2),
    ERROR_ISSUE(R.string.withdraw_reason3),
    CONTENT_ISSUE(R.string.withdraw_reason4),
}
