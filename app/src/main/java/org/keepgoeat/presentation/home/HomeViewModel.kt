package org.keepgoeat.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.keepgoeat.domain.model.HomeGoal
import org.keepgoeat.domain.repository.GoalRepository
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val goalRepository: GoalRepository,
) : ViewModel() {
    private var _goalList = MutableLiveData<MutableList<HomeGoal>>()
    val goalList: LiveData<MutableList<HomeGoal>> get() = _goalList
    private val _goalCount = MutableLiveData<Int>()
    val goalCount: LiveData<Int> get() = _goalCount
    private val _hour = MutableLiveData(LocalDateTime.now().hour)
    val hour: LiveData<Int> get() = _hour
    private val _cheeringMessage = MutableLiveData<String>()
    val cheeringMessage: LiveData<String> get() = _cheeringMessage
    private val _achievedState = MutableLiveData<Boolean>()
    val achievedState: LiveData<Boolean> get() = _achievedState

    init {
        fetchGoalList()
    }

    fun changeGoalAchieved(goal: HomeGoal) {
        val position = goalList.value?.indexOf(goal) ?: return
        viewModelScope.launch {
            goalRepository.achieveGoal(goal.id, !goal.isAchieved)?.let { goalData ->
                with(goal) {
                    _goalList.value?.set(
                        position,
                        HomeGoal(
                            id,
                            goalTitle,
                            isMore,
                            goalData.updatedIsAchieved,
                            goalData.thisMonthCount,
                            type
                        )
                    )
                }
                _achievedState.value = goalData.updatedIsAchieved
                _goalList.value =
                    _goalList.value?.toMutableList() // TODO 서버쪽에서 api 확인해주면 toMutableList 안붙여도 되는지 확인하기
            }
        }
    }

    private fun fetchGoalList() {
        viewModelScope.launch {
            goalRepository.fetchHomeEntireData()?.let { homeData ->
                _cheeringMessage.value = homeData.cheeringMessage
                _goalList.value = homeData.toHomeGoal().toMutableList()
                _goalCount.value = homeData.goals.size
            }
        }
    }
}
