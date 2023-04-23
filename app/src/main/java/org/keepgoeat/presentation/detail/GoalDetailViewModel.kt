package org.keepgoeat.presentation.detail

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.keepgoeat.domain.model.GoalDetail
import org.keepgoeat.domain.model.GoalSticker
import org.keepgoeat.domain.repository.GoalRepository
import org.keepgoeat.presentation.common.MixpanelViewModel
import org.keepgoeat.util.UiState
import org.keepgoeat.util.mixpanel.GoalEvent
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class GoalDetailViewModel @Inject constructor(
    private val goalRepository: GoalRepository,
) : MixpanelViewModel() {
    private val _goalStickers = MutableStateFlow<List<GoalSticker>>(emptyList())
    val goalStickers get() = _goalStickers.asStateFlow()
    private val _goalDetail = MutableStateFlow<GoalDetail?>(null)
    val goalDetail get() = _goalDetail.asStateFlow()
    private val _goalId = MutableStateFlow(-1)
    val goalId get() = _goalId.asStateFlow()
    private val _keepState = MutableStateFlow<UiState<Int>>(UiState.Loading)
    val keepState get() = _keepState.asStateFlow()
    private val _deleteState = MutableStateFlow<UiState<Int>>(UiState.Loading)
    val deleteState get() = _deleteState.asStateFlow()

    fun fetchGoalDetailInfo(goalId: Int) {
        _goalId.value = goalId
        viewModelScope.launch {
            goalRepository.fetchGoalDetail(goalId).onSuccess { detail ->
                _goalDetail.value = detail
                _goalStickers.value = Array(CELL_COUNT) { idx ->
                    GoalSticker(idx, idx < detail.thisMonthCount)
                }.toList()
            }.onFailure {
                Timber.e(it.message)
            }
        }
    }

    fun keepGoal() {
        viewModelScope.launch {
            goalId.value.let { id ->
                goalRepository.keepGoal(id).onSuccess { goalData ->
                    _keepState.value = UiState.Success(goalData.goalId)
                    mixpanelProvider.sendEvent(
                        GoalEvent.archiveGoal(
                            goalDetail.value?.food ?: "",
                            goalDetail.value?.criterion ?: ""
                        )
                    )
                }.onFailure {
                    Timber.e(it.message)
                }
            }
        }
    }

    fun deleteGoal() {
        viewModelScope.launch {
            goalId.value.let { id ->
                goalRepository.deleteGoal(id)
                    .onSuccess { deletedData ->
                        _deleteState.value = UiState.Success(deletedData.goalId)
                        with(mixpanelProvider) {
                            deleteGoal()
                            sendEvent(GoalEvent.deleteGoal(), false)
                        }
                    }.onFailure {
                        Timber.e(it.message)
                    }
            }
        }
    }

    companion object {
        const val CELL_COUNT = 35
    }
}
