package org.keepgoeat.presentation.onboarding

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.keepgoeat.data.datasource.local.KGEDataSource
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(private val localStorage: KGEDataSource) :
    ViewModel() {
    private val _position = MutableStateFlow<Int>(0)
    val position get() = _position.asStateFlow()

    fun setPosition(position: Int) {
        _position.value = position
    }

    fun setClickedOnboardingButton() {
        localStorage.isClickedOnboardingButton = true
    }
}
