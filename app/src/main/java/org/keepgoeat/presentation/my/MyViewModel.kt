package org.keepgoeat.presentation.my

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import org.keepgoeat.domain.model.AchievedGoal
import org.keepgoeat.presentation.type.EatingType
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class MyViewModel @Inject constructor() : ViewModel() {
    private val _eatingType = MutableLiveData<EatingType?>()
    val eatingType: LiveData<EatingType?> get() = _eatingType
    private val _goalList = MutableLiveData<List<AchievedGoal>>()
    val goalList: LiveData<List<AchievedGoal>> get() = _goalList

    init {
        fetchGoalList()
    }

    private fun fetchGoalList() {
        // TODO api 연동 후 remote에서 데이터 불러오기
        _goalList.value = listOf(
            AchievedGoal(1, EatingType.LESS, "라면 덜 먹기", 30, LocalDate.of(2022, 9, 1), LocalDate.of(2022, 12, 31)),
            AchievedGoal(2, EatingType.MORE, "하루 1끼 이상 야채 더 먹기", 30, LocalDate.of(2022, 9, 1), LocalDate.of(2022, 12, 19)),
            AchievedGoal(3, EatingType.LESS, "밀가루 덜 먹기", 30, LocalDate.of(2022, 9, 1), LocalDate.of(2022, 12, 15)),
        )
    }

    fun setEatingType(eatingType: EatingType?) {
        _eatingType.value = eatingType
    }
}
