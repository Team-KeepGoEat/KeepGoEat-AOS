package org.keepgoeat.presentation.type

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import org.keepgoeat.R

enum class WithdrawCheckType(
    @DrawableRes val checkBox: Int,
    @ColorRes val descriptionTextColor: Int,
) {
    CLICKED(
        R.drawable.ic_checkbox_checked,
        R.color.orange_600,
    ),
    DEFAULT(
        R.drawable.ic_checkbox_default,
        R.color.gray_700,
    )
}
