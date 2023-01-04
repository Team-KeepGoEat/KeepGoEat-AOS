package org.keepgoeat.presentation.onboarding

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class OnBoardingItem(
    @StringRes val title: Int,
    @StringRes val des: Int,
    @DrawableRes val image: Int
)
