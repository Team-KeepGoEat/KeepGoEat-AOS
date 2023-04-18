package org.keepgoeat.presentation.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.keepgoeat.domain.repository.GoalRepository
import org.keepgoeat.presentation.model.GoalContent
import org.keepgoeat.presentation.type.EatingType
import org.keepgoeat.util.UiState
import org.keepgoeat.util.mixpanel.GoalEvent
import org.keepgoeat.util.mixpanel.MixpanelProvider
import org.keepgoeat.util.safeLet
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class GoalSettingViewModel @Inject constructor(
    private val goalRepository: GoalRepository,
    private val mixpanelProvider: MixpanelProvider,
) : ViewModel() {
    val goalFood = MutableStateFlow<String?>(null)
    val goalCriterion = MutableStateFlow<String>("")
    private val _eatingType = MutableStateFlow<EatingType?>(null)
    val eatingType get() = _eatingType.asStateFlow()
    var goalId: Int? = null

    private val _uploadState = MutableStateFlow<UiState<Int>>(UiState.Loading)
    val uploadState: StateFlow<UiState<Int>> get() = _uploadState

    fun uploadGoal() {
        if (goalId == null) addGoal()
        else editGoal()
    }

    private fun addGoal() {
        viewModelScope.launch {
            goalRepository.uploadGoalContent(
                goalFood.value?.trim() ?: return@launch,
                goalCriterion.value.trim(),
                eatingType.value == EatingType.MORE
            ).onSuccess {
                _uploadState.value = UiState.Success(it)
                sendGoalAddEvent()
            }.onFailure {
                _uploadState.value = UiState.Error(it.message)
                Timber.e(it.message)
            }
        }
    }

    private fun editGoal() {
        viewModelScope.launch {
            safeLet(goalId, goalFood.value, goalCriterion.value) { id, food, criterion ->
                goalRepository.editGoalContent(id, food, criterion)
                    .onSuccess {
                        _uploadState.value = UiState.Success(it)
                    }.onFailure {
                        _uploadState.value = UiState.Error(it.message)
                        Timber.e(it.message)
                    }
            }
        }
    }

    fun setEatingType(eatingType: EatingType) {
        _eatingType.value = eatingType
    }

    fun setGoalContent(goal: GoalContent) {
        goalId = goal.id
        goalFood.value = goal.food
        goalCriterion.value = goal.criterion
    }

    fun sendGoalAddEvent() {
        val goalType = if (eatingType.value == EatingType.MORE) "더먹기" else "덜먹기"
        mixpanelProvider.sendEvent(GoalEvent.addGoal(goalType))
    }
}
