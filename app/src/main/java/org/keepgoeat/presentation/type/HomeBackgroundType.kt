package org.keepgoeat.presentation.type

import androidx.annotation.DrawableRes
import org.keepgoeat.R

enum class HomeBackgroundType(
    @DrawableRes val sky : Int
) {
    MORNING(R.drawable.img_home_background_morning),
    EVENING(R.drawable.img_home_background_evening),
    NIGHT(R.drawable.img_home_background_night)
}