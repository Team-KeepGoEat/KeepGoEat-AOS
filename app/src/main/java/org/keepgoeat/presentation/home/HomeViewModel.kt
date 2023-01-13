package org.keepgoeat.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.keepgoeat.domain.model.HomeGoal
import org.keepgoeat.domain.repository.GoalRepository
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val goalRepository: GoalRepository,
) : ViewModel() {
    private var _goalList: MutableStateFlow<MutableList<HomeGoal>> =
        MutableStateFlow(mutableListOf())
    val goalList = _goalList.asStateFlow()
    private var _goalCount: MutableStateFlow<Int> = MutableStateFlow(0)
    val goalCount = _goalCount.asStateFlow()
    private var _hour: MutableStateFlow<Int> = MutableStateFlow(LocalDateTime.now().hour)
    val hour = _hour.asStateFlow()
    private var _cheeringMessage: MutableStateFlow<String> = MutableStateFlow("")
    val cheeringMessage = _cheeringMessage.asStateFlow()
    private var _achievedState: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val achievedState = _achievedState.asStateFlow()

    init {
        fetchGoalList()
    }

    fun changeGoalAchieved(goal: HomeGoal) {
        viewModelScope.launch {
            goalRepository.achieveGoal(goal.id, !goal.isAchieved)?.let { goalData ->
                with(goal) {
                    val newGoal = HomeGoal(
                        id,
                        goalTitle,
                        isMore,
                        goalData.updatedIsAchieved,
                        goalData.thisMonthCount,
                        type
                    )
                    _goalList.value.map { homeGoal ->
                        if (homeGoal.id == id)
                            newGoal
                        else homeGoal
                    }
                }
                if (goalData.updatedIsAchieved)
                    _achievedState.update { true }
            }
        }
    }

    private fun fetchGoalList() {
        viewModelScope.launch {
            goalRepository.fetchHomeEntireData()?.let { homeData ->
                _cheeringMessage.update { homeData.cheeringMessage }
                _achievedState.update { false }
                _goalCount.update { homeData.goals.size }
                _goalList.update { homeData.toHomeGoal().toMutableList() }
            }
        }
    }
}
