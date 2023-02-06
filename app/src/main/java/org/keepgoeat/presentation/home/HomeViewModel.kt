package org.keepgoeat.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.keepgoeat.data.ApiResult
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
    val goalList get() = _goalList
    private val _goalCount = MutableLiveData<Int>()
    val goalCount: LiveData<Int> get() = _goalCount
    private val _hour = MutableLiveData(LocalDateTime.now().hour)
    val hour: LiveData<Int> get() = _hour
    private val _cheeringMessage = MutableLiveData<String>()
    val cheeringMessage: LiveData<String> get() = _cheeringMessage
    private val _achievedState = MutableLiveData<Boolean>() // Lottie 변경을 위한 변수
    val achievedState: LiveData<Boolean> get() = _achievedState

    init {
        fetchGoalList()
    }

    fun changeGoalAchieved(goal: HomeGoal, adapter: HomeGoalAdapter) {
        val position = goalList.value.indexOf(goal)
        viewModelScope.launch {
            goalRepository.achieveGoal(goal.id, !goal.isAchieved)?.let { goalData ->
                with(goal) {
                    _goalList.value.set(
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
                    _goalList.value.toMutableList()
                Timber.d(goalList.value.toString())
                adapter.submitList(goalList.value.toMutableList())
            }
        }
    }

    private fun fetchGoalList() {
        viewModelScope.launch {
            goalRepository.fetchHomeEntireData()
                .collectLatest { result ->
                    val homeData = when (result) {
                        is ApiResult.Success -> {
                            result.data
                        }
                        is ApiResult.NetworkError -> {
                            Timber.d("Network Error")
                            null
                        }
                        is ApiResult.GenericError -> {
                            Timber.d("(${result.code}): $(result.message}")
                            null
                        }
                    }
                    if (homeData != null) {
                        _goalList.value = homeData.toHomeGoal().toMutableList()
                        _cheeringMessage.value = homeData.cheeringMessage.replace("\\n", "\n")
                        _achievedState.value = false
                        _goalCount.value = homeData.goals.size
                    }
                }
        }
    }
}
