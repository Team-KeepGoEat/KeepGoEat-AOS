package org.keepgoeat.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.keepgoeat.presentation.type.HomeGoalViewType

class HomeViewModel : ViewModel() {
    private val _goalList = MutableLiveData<MutableList<MyGoalInfo>>()
    val goalList: LiveData<MutableList<MyGoalInfo>> get() = _goalList
    private val _goalCount = MutableLiveData<Int>()
    val goalCount: LiveData<Int> get() = _goalCount

    init {
        fetchGoalList()
    }

    fun changeGoalAchieved(myGoal: MyGoalInfo) {
        val position = goalList.value?.indexOf(myGoal) ?: return
        with(myGoal) {
            _goalList.value?.set(
                position,
                MyGoalInfo(
                    goalName, goalDate, moreGoal, !goalAchieved, type
                )
            )
        }
        _goalList.value = _goalList.value
    }

    private fun fetchGoalList() {
        var myGoalList = mutableListOf(
            MyGoalInfo(
                "하루 1끼 이상 야채 더 먹기",
                "8",
                true,
                false,
                HomeGoalViewType.MY_GOAL_TYPE
            ),
            MyGoalInfo(
                "라면 덜 먹기",
                "8",
                false,
                false,
                HomeGoalViewType.MY_GOAL_TYPE
            ),
            MyGoalInfo(
                "커피 덜 먹기",
                "30",
                false,
                true,
                HomeGoalViewType.MY_GOAL_TYPE
            )
        )
//        val myGoalList = emptyList<MyGoalInfo>()
        val addGoalBtn = mutableListOf(
            MyGoalInfo(
                "3",
                "",
                false,
                false,
                HomeGoalViewType.ADD_GOAL_TYPE
            )
        )
        var homeList = mutableListOf<MyGoalInfo>()
        homeList.addAll(myGoalList)
        if (myGoalList.size > 0)
            homeList.addAll(addGoalBtn)
        _goalList.value = homeList.toMutableList()
        _goalCount.value = myGoalList.size
    }
}
