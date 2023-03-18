package org.keepgoeat.presentation.my

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.keepgoeat.data.datasource.local.KGEDataSource
import org.keepgoeat.domain.model.AchievedGoal
import org.keepgoeat.domain.model.UserInfo
import org.keepgoeat.domain.repository.AuthRepository
import org.keepgoeat.domain.repository.GoalRepository
import org.keepgoeat.domain.repository.UserRepository
import org.keepgoeat.presentation.model.WithdrawReason
import org.keepgoeat.presentation.type.SortType
import org.keepgoeat.util.UiState
import org.keepgoeat.util.extension.toStateFlow
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MyViewModel @Inject constructor(
    // TODO need refactoring
    private val authRepository: AuthRepository,
    private val goalRepository: GoalRepository,
    private val userRepository: UserRepository,
    private val localStorage: KGEDataSource,
) : ViewModel() {
    private val _userInfo = MutableStateFlow(UserInfo("", "", 0))
    val userInfo get() = _userInfo.asStateFlow()
    private val _achievedGoalUiState =
        MutableStateFlow<UiState<List<AchievedGoal>>>(UiState.Loading)
    val achievedGoalUiState get() = _achievedGoalUiState.asStateFlow()
    private val _logoutUiState = MutableStateFlow<UiState<Boolean>>(UiState.Loading)
    val logoutUiState get() = _logoutUiState.asStateFlow()
    private val _achievedGoalCount = MutableStateFlow(0)
    private val _deleteState = MutableStateFlow<UiState<Int>>(UiState.Loading)
    private val _allAchievedGoalCount = MutableStateFlow(0)
    val deleteState get() = _deleteState.asStateFlow()
    val achievedGoalCount get() = _achievedGoalCount.asStateFlow()
    val allAchievedGoalCount get() = _allAchievedGoalCount.asStateFlow()
    private val _deleteAccountUiState =
        MutableStateFlow<UiState<Boolean>>(UiState.Loading)
    val deleteAccountUiState get() = _deleteAccountUiState.asStateFlow()
    val otherReason = MutableStateFlow<String?>("")
    val isValidOtherReason: StateFlow<Boolean>
        get() = otherReason.map { reason ->
            !reason.isNullOrBlank()
        }.toStateFlow(viewModelScope, false)
    private val _isKeyboardVisible = MutableStateFlow(false)
    val isKeyboardVisible get() = _isKeyboardVisible.asStateFlow()
    private val _isOtherReasonSelected = MutableStateFlow(false)
    val isOtherReasonSelected get() = _isOtherReasonSelected.asStateFlow()
    private val _selectedReasons = MutableStateFlow(arrayListOf(WithdrawReason.REASON5))
    val selectedReasons get() = _selectedReasons.asStateFlow()
    val loginPlatForm = localStorage.loginPlatform

    fun fetchUserInfo() {
        viewModelScope.launch {
            userRepository.fetchUserInfo().onSuccess { userInfo ->
                userInfo?.let {
                    _userInfo.value = userInfo
                }
            }
        }
    }

    fun fetchAchievedGoalBySort(sortType: SortType) {
        viewModelScope.launch {
            goalRepository.fetchAchievedGoal(sortType.name.lowercase())
                .onSuccess {
                    _achievedGoalUiState.value = UiState.Success(it)
                    _achievedGoalCount.value = it.size
                    if (sortType == SortType.ALL)
                        _allAchievedGoalCount.value = it.size
                }.onFailure {
                    _achievedGoalUiState.value = UiState.Error(null)
                }
        }
    }

    fun deleteGoal(id: Int) {
        _goalId.value = id
        viewModelScope.launch {
            goalId.value.let { id ->
                goalRepository.deleteGoal(id).onSuccess { deletedData ->
                    _deleteState.value = UiState.Success(deletedData.goalId)
                    _achievedGoalCount.value -= 1
                    _allAchievedGoalCount.value -= 1
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

    fun setKeyboardVisibility(visible: Boolean) {
        _isKeyboardVisible.value = visible
    }

    fun onCheckBoxClick() {
        _isOtherReasonSelected.value = !isOtherReasonSelected.value
    }

    fun changeCheckboxSelected(isSelected: Boolean) {
        _isOtherReasonSelected.value = isSelected
    }

    fun selectReasons(isSelected: WithdrawReason) {
        if (_selectedReasons.value.contains(isSelected)) {
            _selectedReasons.value.remove(isSelected)
            return
        }
        _selectedReasons.value.add(isSelected)
    }
}
