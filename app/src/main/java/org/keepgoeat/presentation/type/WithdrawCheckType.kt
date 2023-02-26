package org.keepgoeat.presentation.type

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StyleRes
import org.keepgoeat.R

enum class WithdrawCheckType(
    @DrawableRes val checkBox: Int,
    @ColorRes val descriptionTextColor: Int,
    @StyleRes val textStyle: Int,
) {
    CLICKED(
        R.drawable.ic_checkbox_checked,
        R.color.orange_600,
        R.style.TextAppearance_System4_Bold,
    ),
    DEFAULT(
        R.drawable.ic_checkbox_default,
        R.color.gray_700,
        R.style.TextAppearance_System4,
    )
}
