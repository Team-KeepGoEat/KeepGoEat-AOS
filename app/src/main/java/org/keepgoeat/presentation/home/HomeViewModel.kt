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
                _goalCount.value = homeData.goals.size
            }
        }
    }
}
