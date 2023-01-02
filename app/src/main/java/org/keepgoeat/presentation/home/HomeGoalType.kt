package org.keepgoeat.presentation.home

import android.graphics.Color
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import org.keepgoeat.R

enum class HomeGoalType(
    @StringRes val goalTypeRes: Int,
    @ColorRes val tagColorRes: Int,
    @DrawableRes val tagDrawableRes: Int,
    @ColorRes val tagTextColorRes: Int
) {
    MORE(
        R.string.goal_more,
        R.color.orange_100,
        R.drawable.ic_tag_plus,
        R.color.orange_600
    ),
    LESS(
        R.string.goal_less,
        R.color.green_200,
        R.drawable.ic_tag_minus,
        R.color.green_600
    )
}