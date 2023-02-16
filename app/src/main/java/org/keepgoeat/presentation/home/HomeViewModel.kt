package org.keepgoeat.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.keepgoeat.domain.model.HomeGoal
import org.keepgoeat.domain.repository.GoalRepository
import org.keepgoeat.presentation.type.ProcessState
import timber.log.Timber
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val goalRepository: GoalRepository,
) : ViewModel() {
    private var _goalList = MutableStateFlow<MutableList<HomeGoal>>(mutableListOf())
    val goalList get() = _goalList.asStateFlow()
    private val _goalCount = MutableStateFlow(0)
    val goalCount = _goalCount.asStateFlow()
    private val _hour = MutableStateFlow(LocalDateTime.now().hour)
    val hour = _hour.asStateFlow()
    private val _cheeringMessage = MutableStateFlow("")
    val cheeringMessage = _cheeringMessage.asStateFlow()
    private val _lottieState = MutableStateFlow(ProcessState.IDLE)
    val lottieState get() = _lottieState.asStateFlow()

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
                    if (goalData.updatedIsAchieved) {
                        _lottieState.value = ProcessState.IN_PROGRESS
                    }
                    _goalList.value =
                        list.toMutableList()
                }
                .onFailure {
                    Timber.e(it.message)
                }
        }
    }

    private fun fetchGoalList() {
        viewModelScope.launch {
            goalRepository.fetchHomeEntireData()
                .onSuccess { homeData ->
                    _goalList.value = homeData.toHomeGoal().toMutableList()
                    _cheeringMessage.value = homeData.cheeringMessage.replace("\\n", "\n")
                    _lottieState.value = ProcessState.IDLE
                    _goalCount.value = homeData.goals.size
                }.onFailure {
                    Timber.e(it.message)
                }
        }
    }

    fun changeLottieState(state: ProcessState) {
        _lottieState.value = state
    }
}
