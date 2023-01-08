package org.keepgoeat.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.keepgoeat.domain.model.HomeMyGoal
import org.keepgoeat.domain.repository.HomeRepository
import org.keepgoeat.presentation.type.HomeGoalViewType
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeRepository: HomeRepository
) : ViewModel() {
    private val _goalList = MutableLiveData<MutableList<HomeMyGoal>>()
    val goalList: LiveData<MutableList<HomeMyGoal>> get() = _goalList
    private val _goalCount = MutableLiveData<Int>()
    val goalCount: LiveData<Int> get() = _goalCount
    private val _hour = MutableLiveData(LocalDateTime.now().hour)
    val hour: LiveData<Int> get() = _hour
    private val _cheeringMessage = MutableLiveData<String>()
    val cheeringMessage: LiveData<String> get() = _cheeringMessage

    init {
        fetchGoalList()
    }

    fun changeGoalAchieved(myGoal: HomeMyGoal) {
        val position = goalList.value?.indexOf(myGoal) ?: return
        with(myGoal) {
            _goalList.value?.set(
                position,
                HomeMyGoal(
                    id, goalTitle, isMore, !isAchieved, thisMonthCount, type
                )
            )
        }
        _goalList.value = _goalList.value
    }

    private fun fetchGoalList() {
        viewModelScope.launch {
            homeRepository.fetchHome()?.let { homeData ->
                _cheeringMessage.value = homeData.cheeringMessage
                _goalList.value = homeData.toHomeMyGoal().toMutableList()
            }
        }

        var myGoalList = mutableListOf(
            HomeMyGoal(
                1,
                "하루 1끼 이상 야채 더 먹기",
                true,
                true,
                8,
                HomeGoalViewType.MY_GOAL_TYPE
            ),
            HomeMyGoal(
                2,
                "라면 덜 먹기",
                false,
                false,
                8,
                HomeGoalViewType.MY_GOAL_TYPE
            ),
            HomeMyGoal(
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
            HomeMyGoal(
                0,
                "",
                false,
                false,
                0,
                HomeGoalViewType.ADD_GOAL_TYPE
            )
        )
        var homeList = mutableListOf<HomeMyGoal>()
        homeList.addAll(myGoalList)
        if (myGoalList.size > 0)
            homeList.addAll(addGoalBtn)
        _goalList.value = homeList.toMutableList()
        _goalCount.value = myGoalList.size
    }
}
