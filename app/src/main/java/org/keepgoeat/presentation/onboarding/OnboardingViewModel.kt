package org.keepgoeat.presentation.onboarding

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.keepgoeat.data.datasource.local.KGEDataSource
import org.keepgoeat.presentation.common.MixpanelViewModel
import org.keepgoeat.presentation.type.OnBoardingViewType
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(private val localStorage: KGEDataSource) :
    MixpanelViewModel() {
    private val _onboardingType = MutableStateFlow(OnBoardingViewType.FIRST)
    val onboardingType = _onboardingType.asStateFlow()
    val onboardingLastPos = OnBoardingViewType.values().size - 1

    fun setOnboardingType(position: Int) {
        _onboardingType.value = when (position) {
            OnBoardingViewType.FIRST.ordinal -> OnBoardingViewType.FIRST
            OnBoardingViewType.SECOND.ordinal -> OnBoardingViewType.SECOND
            else -> OnBoardingViewType.THIRD
        }
    }

    fun setClickedOnboardingButton() {
        localStorage.isClickedOnboardingButton = true
    }
}
