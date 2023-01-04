package org.keepgoeat.presentation.onboarding

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class OnBoardingViewModel : ViewModel() {
    private val _position: MutableLiveData<Int> = MutableLiveData()
    val position: LiveData<Int>
        get() = _position

    fun setPosition(position: Int) {
        _position.value = position
    }
}
