package org.keepgoeat.presentation.setting

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import org.keepgoeat.presentation.type.EatingType
import javax.inject.Inject

@HiltViewModel
class GoalSettingViewModel @Inject constructor() : ViewModel() {
    val goalTitle = MutableLiveData<String>()
    private val _eatingType = MutableLiveData<EatingType>()
    val eatingType: LiveData<EatingType> get() = _eatingType

    val isValidTitle: LiveData<Boolean>
        get() = Transformations.map(goalTitle) { title ->
            title.length in 1..20 && title.isNotBlank() && title.matches(TITLE_PATTERN.toRegex())
        }

    fun setEatingType(eatingType: EatingType) {
        _eatingType.value = eatingType
    }

    companion object {
        private const val TITLE_PATTERN = "^[A-Za-zㄱ-ㅎ가-힣0-9\\s]*\$"
    }
}
