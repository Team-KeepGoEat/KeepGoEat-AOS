package org.keepgoeat.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.keepgoeat.domain.model.HomeGoal
import org.keepgoeat.domain.repository.GoalRepository
import org.keepgoeat.presentation.type.HomeGoalViewType
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val goalRepository: GoalRepository
) : ViewModel() {
    private val _goalList = MutableLiveData<MutableList<HomeGoal>>()
    val goalList: LiveData<MutableList<HomeGoal>> get() = _goalList
    private val _goalCount = MutableLiveData<Int>()
    val goalCount: LiveData<Int> get() = _goalCount
    private val _hour = MutableLiveData(LocalDateTime.now().hour)
    val hour: LiveData<Int> get() = _hour
    private val _cheeringMessage = MutableLiveData<String>()
    val cheeringMessage: LiveData<String> get() = _cheeringMessage

    init {
        fetchGoalList()
    }

    fun changeGoalAchieved(myGoal: HomeGoal) {
        val position = goalList.value?.indexOf(myGoal) ?: return
        with(myGoal) {
            _goalList.value?.set(
                position,
                HomeGoal(
                    id, goalTitle, isMore, !isAchieved, thisMonthCount, type
                )
            )
        }
        _goalList.value = _goalList.value
    }

    private fun fetchGoalList() {
        viewModelScope.launch {
            goalRepository.fetchHomeEntireData()?.let { homeData ->
                _cheeringMessage.value = homeData.cheeringMessage
                _goalList.value = homeData.toHomeGoal().toMutableList()
            }
        }

        var myGoalList = mutableListOf(
            HomeGoal(
                1,
                "하루 1끼 이상 야채 더 먹기",
                true,
                true,
                8,
                HomeGoalViewType.MY_GOAL_TYPE
            ),
            HomeGoal(
                2,
                "라면 덜 먹기",
                false,
                false,
                8,
                HomeGoalViewType.MY_GOAL_TYPE
            ),
            HomeGoal(
                3,
                "커피 덜 먹기",
                true,
                false,
                30,
                HomeGoalViewType.MY_GOAL_TYPE
            )
        )
//        val myGoalList = emptyList<HomeMyGoal>()
        val addGoalBtn = mutableListOf(
            HomeGoal(
                0,
                "",
                false,
                false,
                0,
                HomeGoalViewType.ADD_GOAL_TYPE
            )
        )
        var homeList = mutableListOf<HomeGoal>()
        homeList.addAll(myGoalList)
        if (myGoalList.size > 0)
            homeList.addAll(addGoalBtn)
        _goalList.value = homeList.toMutableList()
        _goalCount.value = myGoalList.size
    }
}
