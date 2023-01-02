package org.keepgoeat.presentation.home

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import org.keepgoeat.R

enum class HomeBtnType(
    @ColorRes val btnColorInt : Int,
    @DrawableRes val btnDrawableRes: Int,
    @StringRes val btnStringRes: Int,
    @ColorRes val btnTextColorInt : Int
) {
    MINUS_ACHIEVED(
        R.color.green_100,
        R.drawable.ic_check_green,
        R.string.home_goal_less_achieved,
        R.color.green_600
    ),
    MINUS_NOT_ACHIEVED(
        R.color.green_500,
        R.drawable.ic_check,
        R.string.home_goal_less_btn,
        R.color.gray_50
    ),
    PLUS_ACHIEVED(
    R.color.orange_50,
    R.drawable.ic_check_orange,
    R.string.home_goal_more_achieved,
    R.color.orange_600
    ),
    PLUS_NOT_ACHIEVED(
    R.color.orange_600,
    R.drawable.ic_check,
    R.string.home_goal_more_btn,
    R.color.gray_50
    )
}