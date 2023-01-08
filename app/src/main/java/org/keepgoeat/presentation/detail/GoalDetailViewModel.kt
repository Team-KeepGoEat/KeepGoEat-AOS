package org.keepgoeat.presentation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import org.keepgoeat.domain.model.GoalSticker
import org.keepgoeat.presentation.type.EatingType
import javax.inject.Inject

@HiltViewModel
class GoalDetailViewModel @Inject constructor() : ViewModel() {
    private val _goalStickers = MutableLiveData<List<GoalSticker>>()
    val goalStickers: LiveData<List<GoalSticker>> get() = _goalStickers
    private val _goalTitle = MutableLiveData<String>()
    val goalTitle: LiveData<String> get() = _goalTitle
    private val _numOfDaysEatenLastMonth = MutableLiveData<Int>()
    val numOfDaysEatenLastMonth: LiveData<Int> get() = _numOfDaysEatenLastMonth
    private val _numOfDaysEatenThisMonth = MutableLiveData<Int>()
    val numOfDaysEatenThisMonth: LiveData<Int> get() = _numOfDaysEatenThisMonth
    private var _eatingType = MutableLiveData<EatingType>()
    val eatingType: LiveData<EatingType> get() = _eatingType
    private var _goalId = MutableLiveData<Int>()
    val goalId: LiveData<Int> get() = _goalId

    // TODO api 연동 후 해당 주석 제거 예정
//    init {
//        fetchGoalDetailInfo()
//    }

    fun fetchGoalDetailInfo() {
        // TODO api 연동 후 remote에서 데이터 불러오기
        val totalAchievementDays = 8
        _goalStickers.value = Array(CELL_COUNT) { idx ->
            GoalSticker(idx, idx < totalAchievementDays)
        }.toList()

        _goalTitle.value = if (eatingType.value == EatingType.MORE) "하루 1끼 이상 야채" else "라면"
        _numOfDaysEatenLastMonth.value = 4
        _numOfDaysEatenThisMonth.value = 8
    }

    fun keepGoal() {
        // TODO api 연동 필요
    }

    fun deleteGoal() {
        // TODO api 연동 필요
    }

    fun setEatingType(eatingType: EatingType) {
        _eatingType.value = eatingType
    }

    fun setGoalId(id: Int) {
        _goalId.value = id
    }

    companion object {
        const val CELL_COUNT = 35
    }
}
