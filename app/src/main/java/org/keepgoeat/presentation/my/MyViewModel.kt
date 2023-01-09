package org.keepgoeat.presentation.my

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.keepgoeat.domain.model.MyGoal
import org.keepgoeat.domain.repository.MyRepository
import org.keepgoeat.presentation.type.SortType
import javax.inject.Inject

@HiltViewModel
class MyViewModel @Inject constructor(private val myRepository: MyRepository) : ViewModel() {
    private val _goalList = MutableLiveData<List<MyGoal>>()
    val goalList: LiveData<List<MyGoal>> get() = _goalList

    init {
        fetchAchievedGoalBySort(SortType.ALL)
    }

    fun fetchAchievedGoalBySort(sortType: SortType) {
        viewModelScope.launch {
            myRepository.fetchMyData(sortType.name.lowercase())?.let { MyData ->
                _goalList.value = MyData.toMyGoal()
            }
        }
    }
}
