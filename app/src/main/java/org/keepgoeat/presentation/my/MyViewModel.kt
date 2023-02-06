package org.keepgoeat.presentation.my

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.keepgoeat.domain.model.MyGoal
import org.keepgoeat.domain.repository.MyRepository
import org.keepgoeat.presentation.type.SortType
import org.keepgoeat.util.UiState
import javax.inject.Inject

@HiltViewModel
class MyViewModel @Inject constructor(private val myRepository: MyRepository) : ViewModel() {
    private val _goalList = MutableStateFlow<UiState<List<MyGoal>>>(UiState.Loading)
    val goalList get() = _goalList.asStateFlow()

    init {
        fetchAchievedGoalBySort(SortType.ALL)
    }

    fun fetchAchievedGoalBySort(sortType: SortType) {
        viewModelScope.launch {
            myRepository.fetchMyData(sortType.name.lowercase())
                .onSuccess {
                    _goalList.value = UiState.Success(it)
                }.onFailure {
                    _goalList.value = UiState.Error(it.message)
                }
        }
    }
}
