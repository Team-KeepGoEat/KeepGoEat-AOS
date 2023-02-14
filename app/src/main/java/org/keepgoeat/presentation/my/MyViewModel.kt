package org.keepgoeat.presentation.my

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.keepgoeat.data.datasource.local.KGEDataSource
import org.keepgoeat.domain.model.MyGoal
import org.keepgoeat.domain.repository.AuthRepository
import org.keepgoeat.domain.repository.MyRepository
import org.keepgoeat.presentation.type.SortType
import org.keepgoeat.util.UiState
import javax.inject.Inject

@HiltViewModel
class MyViewModel @Inject constructor(
    private val myRepository: MyRepository,
    private val authRepository: AuthRepository,
    private val localStorage: KGEDataSource,
) : ViewModel() {
    private val _achievedGoalUiState = MutableStateFlow<UiState<List<MyGoal>>>(UiState.Loading)
    val achievedGoalUiState get() = _achievedGoalUiState.asStateFlow()
    private val _logoutUiState = MutableStateFlow<UiState<Boolean>>(UiState.Loading)
    val logoutUiState get() = _deleteAccountUiState.asStateFlow()
    private val _deleteAccountUiState =
        MutableStateFlow<UiState<Boolean>>(UiState.Loading)
    val deleteAccountUiState get() = _deleteAccountUiState.asStateFlow()
    val loginPlatForm = localStorage.loginPlatform

    init {
        fetchAchievedGoalBySort(SortType.ALL)
    }

    fun fetchAchievedGoalBySort(sortType: SortType) {
        viewModelScope.launch {
            myRepository.fetchMyData(sortType.name.lowercase())
                .onSuccess {
                    _achievedGoalUiState.value = UiState.Success(it)
                }.onFailure {
                    _achievedGoalUiState.value = UiState.Error(null)
                }
        }
    }

    fun logout() {
        localStorage.clear()
        _logoutUiState.value = UiState.Success(true)
    }

    fun deleteAccount() {
        viewModelScope.launch {
            authRepository.deleteAccount()
                .onSuccess {
                    _deleteAccountUiState.value = UiState.Success(true)
                }.onFailure {
                    _deleteAccountUiState.value = UiState.Error(it.message)
                }
        }
    }
}
