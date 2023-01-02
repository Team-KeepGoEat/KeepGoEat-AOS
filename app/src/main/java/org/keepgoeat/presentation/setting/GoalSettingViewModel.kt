package org.keepgoeat.presentation.setting

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import org.keepgoeat.presentation.type.EatingType
import javax.inject.Inject

@HiltViewModel
class GoalSettingViewModel @Inject constructor() : ViewModel() {
    val goalTitle = MutableLiveData<String>()
    private val _isValidGoalTitleFormat = MutableLiveData<Boolean>()
    val isValidGoalTitleFormat: LiveData<Boolean> get() = _isValidGoalTitleFormat
    private val _eatingType = MutableLiveData<EatingType>()
    val eatingType: LiveData<EatingType> get() = _eatingType

    fun setEatingType(eatingType: EatingType) {
        _eatingType.value = eatingType
    }
}
