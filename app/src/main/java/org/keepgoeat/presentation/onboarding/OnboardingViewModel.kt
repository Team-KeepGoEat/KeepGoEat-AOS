package org.keepgoeat.presentation.onboarding

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import org.keepgoeat.data.datasource.local.KGEDataSource
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(private val localStorage: KGEDataSource) :
    ViewModel() {
    private val _position: MutableLiveData<Int> = MutableLiveData()
    val position: LiveData<Int>
        get() = _position

    fun setPosition(position: Int) {
        _position.value = position
    }

    fun setClickedOnboardingButton() {
        localStorage.isClickedOnboardingButton = true
    }
}
