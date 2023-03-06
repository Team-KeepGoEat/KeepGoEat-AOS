package org.keepgoeat.presentation.my

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.keepgoeat.data.datasource.local.KGEDataSource
import org.keepgoeat.domain.model.AchievedGoal
import org.keepgoeat.domain.repository.AuthRepository
import org.keepgoeat.domain.repository.GoalRepository
import org.keepgoeat.presentation.type.SortType
import org.keepgoeat.util.UiState
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MyViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val goalRepository: GoalRepository,
    private val localStorage: KGEDataSource,
) : ViewModel(), AchievedGoalAdapter.updateGoalIdListener {
    private val _goalId = MutableStateFlow(-1)
    val goalId get() = _goalId.asStateFlow()
    private val _achievedGoalUiState =
        MutableStateFlow<UiState<List<AchievedGoal>>>(UiState.Loading)
    val achievedGoalUiState get() = _achievedGoalUiState.asStateFlow()
    private val _logoutUiState = MutableStateFlow<UiState<Boolean>>(UiState.Loading)
    val logoutUiState get() = _logoutUiState.asStateFlow()
    private val _achievedGoalCount = MutableStateFlow(0)
    private val _deleteState = MutableStateFlow<UiState<Int>>(UiState.Loading)
    val deleteState get() = _deleteState.asStateFlow()
    val achievedGoalCount get() = _achievedGoalCount.asStateFlow()
    private val _deleteAccountUiState =
        MutableStateFlow<UiState<Boolean>>(UiState.Loading)
    val deleteAccountUiState get() = _deleteAccountUiState.asStateFlow()
    val loginPlatForm = localStorage.loginPlatform
    val userName = localStorage.userName
    val userEmail = localStorage.userEmail

    init {
        fetchAchievedGoalBySort(SortType.ALL)
    }

    override fun updateGoalId(data: AchievedGoal) {
        _goalId.value = data.id
    }

    fun fetchAchievedGoalBySort(sortType: SortType) {
        viewModelScope.launch {
            goalRepository.fetchAchievedGoal(sortType.name.lowercase())
                .onSuccess {
                    _achievedGoalUiState.value = UiState.Success(it)
                    _achievedGoalCount.value = it.size
                }.onFailure {
                    _achievedGoalUiState.value = UiState.Error(null)
                }
        }
    }

    fun deleteGoal() {
        viewModelScope.launch {
            goalId.value.let { id ->
                goalRepository.deleteGoal(id).onSuccess { deletedData ->
                    _deleteState.value = UiState.Success(deletedData.goalId)
                }.onFailure {
                    Timber.e(it.message)
                }
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
