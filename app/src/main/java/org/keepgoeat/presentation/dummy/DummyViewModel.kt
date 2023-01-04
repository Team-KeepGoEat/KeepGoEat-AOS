package org.keepgoeat.presentation.dummy

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.keepgoeat.domain.model.DummyData
import org.keepgoeat.domain.repository.DummyRepository
import org.keepgoeat.util.UiState
import org.keepgoeat.util.toUiState
import javax.inject.Inject

@HiltViewModel
class DummyViewModel @Inject constructor(private val dummyRepository: DummyRepository) :
    ViewModel() {
    private val _dummyList = MutableLiveData<UiState<List<DummyData>>>(UiState.Loading)
    val dummyList: LiveData<UiState<List<DummyData>>> get() = _dummyList

    fun uploadDummy() {
        viewModelScope.launch {
            dummyRepository.uploadDummy("keepgoeat", "keepgoeat@gmail.com").let { dummy ->
                _dummyList.value = dummy?.toDummyDataList()?.toUiState()
            }
        }
    }
}
