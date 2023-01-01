package org.keepgoeat.presentation.type

import android.graphics.Color
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import org.keepgoeat.R

enum class EatingType(
    @StringRes val strRes: Int,
    @DrawableRes val snailStickerRes: Int,
    @DrawableRes val defaultStickerRes: Int,
    @DrawableRes val tagIconRes: Int,
    @ColorRes val tagTextColor: Int,
    @ColorRes val tagBackgroundColor: Int,
    @ColorRes val thisMonthTextColor: Int,
    @ColorInt val cardBackgroundColor: Int,
) {
    LESS(
        R.string.eating_type_less,
        R.drawable.ic_snail_green_sticker,
        R.drawable.ic_default_green_sticker,
        R.drawable.ic_eating_less_tag_minus,
        R.color.green_600,
        R.color.green_200,
        R.color.green_700,
        Color.parseColor("#EAFBF4"),
    ),
    MORE(
        R.string.eating_type_more,
        R.drawable.ic_snail_orange_sticker,
        R.drawable.ic_default_orange_sticker,
        R.drawable.ic_eating_more_tag_plus,
        R.color.orange_600,
        R.color.orange_100,
        R.color.orange_600,
        Color.parseColor("#FFF0EB"),
    )
}
