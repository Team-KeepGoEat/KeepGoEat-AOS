package org.keepgoeat.presentation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.keepgoeat.domain.model.GoalDetail
import org.keepgoeat.domain.model.GoalSticker
import org.keepgoeat.domain.repository.GoalRepository
import org.keepgoeat.util.UiState
import javax.inject.Inject

@HiltViewModel
class GoalDetailViewModel @Inject constructor(private val goalRepository: GoalRepository) :
    ViewModel() {
    private val _goalStickers = MutableLiveData<List<GoalSticker>>()
    val goalStickers: LiveData<List<GoalSticker>> get() = _goalStickers
    private val _goalDetail = MutableLiveData<GoalDetail>()
    val goalDetail: LiveData<GoalDetail> get() = _goalDetail
    private val _goalId = MutableLiveData<Int>()
    val goalId: LiveData<Int> get() = _goalId
    private val _keepState = MutableLiveData<UiState<Int>>()
    val keepState: LiveData<UiState<Int>> get() = _keepState

    fun fetchGoalDetailInfo(goalId: Int) {
        _goalId.value = goalId
        viewModelScope.launch {
            goalRepository.fetchGoalDetail(goalId).let { detail ->
                _goalDetail.value = detail?.toGoalDetail() ?: return@launch
                _goalStickers.value = Array(CELL_COUNT) { idx ->
                    GoalSticker(idx, idx < detail.thisMonthCount)
                }.toList()
            }
        }
    }

    fun keepGoal() {
        viewModelScope.launch {
            goalId.value?.let { id ->
                goalRepository.keepGoal(id).let { keptData ->
                    keptData ?: return@launch
                    _keepState.value = UiState.Success(keptData.goalId)
                }
            }
        }
    }

    fun deleteGoal() {
        // TODO api 연동 필요
    }

    companion object {
        const val CELL_COUNT = 35
    }
}
