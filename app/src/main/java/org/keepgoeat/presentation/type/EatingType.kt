package org.keepgoeat.presentation.type

import android.graphics.Color
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import org.keepgoeat.R

enum class EatingType(
    @StringRes val strRes: Int,
    @StringRes val titleStrRes: Int,
    @StringRes val achievementCountTitle: Int,
    @DrawableRes val snailStickerRes: Int,
    @DrawableRes val defaultStickerRes: Int,
    @DrawableRes val tagIconRes: Int,
    @DrawableRes val snailCheerInKeepRes: Int,
    @ColorRes val tagTextColor: Int,
    @ColorRes val tagBackgroundColor: Int,
    @ColorRes val thisMonthTextColor: Int,
    @ColorRes val buttonBackgroundColor: Int,
    @ColorRes val buttonTextColor: Int,
    @ColorRes val buttonRippleColor: Int,
    @ColorRes val achievedGoalDayTextColor: Int,
    @ColorInt val cardBackgroundColor: Int,
) {
    LESS(
        R.string.eating_type_less,
        R.string.title_less,
        R.string.goal_detail_hold,
        R.drawable.ic_snail_green_sticker,
        R.drawable.ic_default_green_sticker,
        R.drawable.ic_eating_less_tag_minus,
        R.drawable.ic_snail_green_cheer_left,
        R.color.green_600,
        R.color.green_200,
        R.color.green_700,
        R.color.green_500,
        R.color.white,
        R.color.green_700,
        R.color.green_600,
        Color.parseColor("#EAFBF4"),
    ),
    MORE(
        R.string.eating_type_more,
        R.string.title_more,
        R.string.goal_detail_eat,
        R.drawable.ic_snail_orange_sticker,
        R.drawable.ic_default_orange_sticker,
        R.drawable.ic_eating_more_tag_plus,
        R.drawable.ic_snail_orange_cheer_left,
        R.color.orange_600,
        R.color.orange_100,
        R.color.orange_600,
        R.color.orange_600,
        R.color.gray_50,
        R.color.orange_700,
        R.color.orange_500,
        Color.parseColor("#FFF0EB"),
    )
}
