package org.keepgoeat.presentation.home

import android.graphics.Color
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import org.keepgoeat.R

enum class HomeGoalType(
    @StringRes val goalTypeRes: Int,
    @ColorInt val tagColorRes: Int,
    @DrawableRes val tagDrawableRes: Int,
    @ColorInt val textColorRes: Int,
    @ColorInt val btnColorRes: Int,
    @StringRes val btnTextRes: Int
) {
    MORE(
        R.string.goal_more,
        Color.parseColor("#FFD8CC"),
        R.drawable.ic_tag_plus,
        Color.parseColor("#FF774B"),
        Color.parseColor("#FF774B"),
        R.string.home_goal_more_btn
    ),
    LESS(
        R.string.goal_less,
        Color.parseColor("#C7F5E1"),
        R.drawable.ic_tag_minus,
        Color.parseColor("#2ECFA8"),
        Color.parseColor("#73E5B5"),
        R.string.home_goal_less_btn
    )
}