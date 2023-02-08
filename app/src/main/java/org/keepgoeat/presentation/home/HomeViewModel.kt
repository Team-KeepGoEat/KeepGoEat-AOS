package org.keepgoeat.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.keepgoeat.domain.model.HomeGoal
import org.keepgoeat.domain.repository.GoalRepository
import timber.log.Timber
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val goalRepository: GoalRepository,
) : ViewModel() {
    private var _goalList: MutableStateFlow<MutableList<HomeGoal>> =
        MutableStateFlow(mutableListOf())
    val goalList get() = _goalList.asStateFlow()
    private val _goalCount = MutableStateFlow(0)
    val goalCount = _goalCount.asStateFlow()
    private val _hour = MutableStateFlow(LocalDateTime.now().hour)
    val hour = _hour.asStateFlow()
    private val _cheeringMessage = MutableStateFlow("")
    val cheeringMessage = _cheeringMessage.asStateFlow()
    private val _achievedState = MutableStateFlow(false) // Lottie 변경을 위한 변수
    val achievedState get() = _achievedState.asStateFlow()

    init {
        fetchGoalList()
    }

    fun changeGoalAchieved(goal: HomeGoal) {
        val position = goalList.value.indexOf(goal)
        viewModelScope.launch {
            goalRepository.achieveGoal(goal.id, !goal.isAchieved)
                .onSuccess { goalData ->
                    val list = _goalList.value.toMutableList()
                    with(goal) {
                        list.set(
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
                    if (goalData.updatedIsAchieved)
                        _achievedState.value = true
                    _goalList.value =
                        list.toMutableList()
                }
        }
    }

    private fun fetchGoalList() {
        viewModelScope.launch {
            goalRepository.fetchHomeEntireData()
                .onSuccess { homeData ->
                    _goalList.value = homeData.toHomeGoal().toMutableList()
                    _cheeringMessage.value = homeData.cheeringMessage.replace("\\n", "\n")
                    _achievedState.value = false
                    _goalCount.value = homeData.goals.size
                    Timber.d("goalCount : " + goalCount.value)
                }.onFailure {
                    Timber.e(it.message)
                }
        }
    }
}
