package org.keepgoeat.presentation.type

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import org.keepgoeat.R

enum class OnBoardingViewType(
    @StringRes val titleStrRes: Int,
    @StringRes val desStrRes: Int,
    @StringRes val btnTextRes: Int,
    @DrawableRes val imageRes: Int,
) {
    FIRST(R.string.onboarding1_title,
        R.string.onboarding1_des,
        R.string.onboarding1_button,
        R.drawable.img_onboarding_1),
    SECOND(R.string.onboarding2_title,
        R.string.onboarding2_des,
        R.string.onboarding2_button,
        R.drawable.img_onboarding_2),
    THIRD(R.string.onboarding3_title,
        R.string.onboarding3_des,
        R.string.onboarding3_button,
        R.drawable.img_onboarding_3)
}
