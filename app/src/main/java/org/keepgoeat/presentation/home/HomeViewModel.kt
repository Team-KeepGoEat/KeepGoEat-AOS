package org.keepgoeat.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.keepgoeat.domain.model.HomeContent
import org.keepgoeat.domain.model.HomeGoal
import org.keepgoeat.domain.repository.GoalRepository
import org.keepgoeat.presentation.type.ProcessState
import org.keepgoeat.util.UiState
import timber.log.Timber
import java.io.IOException
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val goalRepository: GoalRepository,
) : ViewModel() {
    private var _homeDataFetchState = MutableStateFlow<UiState<HomeContent>>(UiState.Loading)
    val homeDataFetchState get() = _homeDataFetchState.asStateFlow()
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

    fun fetchHomeContent() {
        viewModelScope.launch {
            goalRepository.fetchHomeContent()
                .onSuccess { homeContent ->
                    _homeDataFetchState.value = UiState.Success(homeContent)
                    _goalList.value = homeContent.goals.toMutableList()
                    _cheeringMessage.value = homeContent.cheeringMessage
                    _lottieState.value = ProcessState.IDLE
                    _goalCount.value = homeContent.goals.size
                }.onFailure { throwable ->
                    when (throwable) {
                        is IOException ->
                            _homeDataFetchState.value = UiState.Error(throwable.message)
                    }
                    Timber.e(throwable.message)
                }
        }
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
                                goalCriterion,
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

    fun changeLottieState(state: ProcessState) {
        _lottieState.value = state
    }
}
