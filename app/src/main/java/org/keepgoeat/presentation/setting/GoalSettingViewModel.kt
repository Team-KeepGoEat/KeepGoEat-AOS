package org.keepgoeat.presentation.setting

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.keepgoeat.domain.repository.GoalRepository
import org.keepgoeat.presentation.model.GoalContent
import org.keepgoeat.presentation.type.EatingType
import org.keepgoeat.util.UiState
import org.keepgoeat.util.safeLet
import javax.inject.Inject

@HiltViewModel
class GoalSettingViewModel @Inject constructor(
    private val goalRepository: GoalRepository,
) : ViewModel() {
    val goalTitle = MutableLiveData<String>()
    private val _eatingType = MutableLiveData<EatingType>()
    val eatingType: LiveData<EatingType> get() = _eatingType
    var goalId: Int? = null

    val isValidTitle: LiveData<Boolean>
        get() = Transformations.map(goalTitle) { title ->
            title.length in 1..20 && title.isNotBlank() && title.matches(TITLE_PATTERN.toRegex())
        }

    private val _uploadState = MutableLiveData<UiState<Int>>(UiState.Loading)
    val uploadState: LiveData<UiState<Int>> get() = _uploadState

    fun uploadGoal() {
        viewModelScope.launch {
            goalRepository.uploadGoalContent(goalTitle.value ?: return@launch, eatingType.value == EatingType.MORE).let { result ->
                _uploadState.value = if (result?.id != null) UiState.Success(result.id) else UiState.Empty
            }
        }
    }

    fun editGoal() {
        viewModelScope.launch {
            safeLet(goalId, goalTitle.value) { id, title ->
                goalRepository.editGoalContent(id, title).let { result ->
                    _uploadState.value = if (result?.id != null) UiState.Success(result.id) else UiState.Empty
                }
            }
        }
    }

    fun setEatingType(eatingType: EatingType) {
        _eatingType.value = eatingType
    }

    fun setGoalContent(goal: GoalContent) {
        goalId = goal.id
        goalTitle.value = goal.goalTitle
    }

    companion object {
        private const val TITLE_PATTERN = "^[A-Za-zㄱ-ㅎ가-힣0-9\\s]*\$"
    }
}
