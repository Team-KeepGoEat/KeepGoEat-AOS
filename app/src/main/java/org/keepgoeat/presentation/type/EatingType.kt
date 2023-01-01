package org.keepgoeat.presentation.type

import android.graphics.Color
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import org.keepgoeat.R

enum class EatingType(
    @StringRes val strRes: Int,
    @DrawableRes val snailStickerRes: Int,
    @DrawableRes val defaultStickerRes: Int,
    @ColorInt val cardBackgroundColor: Int,
) {
    LESS(
        R.string.eating_type_less,
        R.drawable.ic_snail_green_sticker,
        R.drawable.ic_default_green_sticker,
        Color.parseColor("#EAFBF4"),
    ),
    MORE(
        R.string.eating_type_more,
        R.drawable.ic_snail_orange_sticker,
        R.drawable.ic_default_orange_sticker,
        Color.parseColor("#FFF0EB"),
    )
}
