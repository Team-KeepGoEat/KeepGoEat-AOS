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
import javax.inject.Inject

@HiltViewModel
class GoalDetailViewModel @Inject constructor(private val goalRepository: GoalRepository) : ViewModel() {
    private val _goalStickers = MutableLiveData<List<GoalSticker>>()
    val goalStickers: LiveData<List<GoalSticker>> get() = _goalStickers
    private val _goalDetail = MutableLiveData<GoalDetail>()
    val goalDetail: LiveData<GoalDetail> get() = _goalDetail
    private val _goalId = MutableLiveData<Int>()
    val goalId: LiveData<Int> get() = _goalId

    fun fetchGoalDetailInfo(goalId: Int) {
        viewModelScope.launch {
            goalRepository.fetchGoalDetail(goalId).let { detail ->
                _goalDetail.value = detail?.toGoalDetail() ?: return@launch
                _goalStickers.value = Array(CELL_COUNT) { idx ->
                    GoalSticker(idx, idx < detail.thisMonthCount)
                }.toList()
            }
        }
    }

    fun keepGoal(goalId: Int) {
        viewModelScope.launch {
            goalRepository.keepGoalDetail(goalId).let { keptData ->
                keptData ?: return@launch
                _goalId.value = keptData.goalId
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
