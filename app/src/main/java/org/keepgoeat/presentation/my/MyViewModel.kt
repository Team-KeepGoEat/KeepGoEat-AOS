package org.keepgoeat.presentation.my

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.keepgoeat.data.datasource.local.KGEDataSource
import org.keepgoeat.domain.model.ArchivedGoal
import org.keepgoeat.domain.model.UserInfo
import org.keepgoeat.domain.repository.AuthRepository
import org.keepgoeat.domain.repository.GoalRepository
import org.keepgoeat.domain.repository.UserRepository
import org.keepgoeat.presentation.base.viewmodel.MixpanelViewModel
import org.keepgoeat.presentation.model.WithdrawReason
import org.keepgoeat.presentation.type.SortType
import org.keepgoeat.util.UiState
import org.keepgoeat.util.extension.toStateFlow
import org.keepgoeat.util.mixpanel.SignEvent
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MyViewModel @Inject constructor(
    // TODO need refactoring
    private val authRepository: AuthRepository,
    private val goalRepository: GoalRepository,
    private val userRepository: UserRepository,
    private val localStorage: KGEDataSource,
) : MixpanelViewModel() {
    private val _userInfo = MutableStateFlow(UserInfo("", "", 0))
    val userInfo get() = _userInfo.asStateFlow()

    private val _archivedGoalFetchUiState =
        MutableStateFlow<UiState<List<ArchivedGoal>>>(UiState.Loading)
    val archivedGoalFetchUiState get() = _archivedGoalFetchUiState.asStateFlow()
    private val _archivedGoalCount = MutableStateFlow(0)
    private val _allArchivedGoalCount = MutableStateFlow(0)

    private val _logoutUiState = MutableStateFlow<UiState<Boolean>>(UiState.Loading)
    val logoutUiState get() = _logoutUiState.asStateFlow()

    private val _goalDeleteState = MutableStateFlow<UiState<Int>>(UiState.Loading)
    val goalDeleteState get() = _goalDeleteState.asStateFlow()
    private var _deletedGoalCount = 0
    val deletedGoalCount get() = _deletedGoalCount

    val archivedGoalCount get() = _archivedGoalCount.asStateFlow()
    val allArchivedGoalCount get() = _allArchivedGoalCount.asStateFlow()

    private val _deleteAccountUiState =
        MutableStateFlow<UiState<Boolean>>(UiState.Loading)
    val deleteAccountUiState get() = _deleteAccountUiState.asStateFlow()
    val otherReason = MutableStateFlow("")
    val isValidOtherReason: StateFlow<Boolean>
        get() = otherReason.map { reason ->
            reason.isNotBlank()
        }.toStateFlow(viewModelScope, false)
    private val _isKeyboardVisible = MutableStateFlow(false)
    val isKeyboardVisible get() = _isKeyboardVisible.asStateFlow()
    private val _isOtherReasonSelected = MutableStateFlow(false)
    val isOtherReasonSelected get() = _isOtherReasonSelected.asStateFlow()
    private val _selectedReasons: MutableStateFlow<ArrayList<WithdrawReason>> =
        MutableStateFlow(arrayListOf())
    private val selectedReasons get() = _selectedReasons.asStateFlow()
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

    fun fetchArchivedGoalBySort(sortType: SortType) {
        viewModelScope.launch {
            goalRepository.fetchArchivedGoal(sortType.name.lowercase())
                .onSuccess {
                    _archivedGoalFetchUiState.value = UiState.Success(it)
                    _archivedGoalCount.value = it.size
                    if (sortType == SortType.ALL)
                        _allArchivedGoalCount.value = it.size
                }.onFailure {
                    _archivedGoalFetchUiState.value = UiState.Error(null)
                }
        }
    }

    fun deleteArchivedGoal(id: Int) {
        viewModelScope.launch {
            goalRepository.deleteGoal(id).onSuccess { deletedData ->
                _goalDeleteState.value = UiState.Success(deletedData.goalId)
                _archivedGoalCount.value -= 1
                _allArchivedGoalCount.value -= 1
                _deletedGoalCount += 1 // TODO need refactoring
            }.onFailure {
                Timber.e(it.message)
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

    fun sendDeleteAccountEvent(reasons: Map<String, Any>?) {
        mixpanelProvider.sendEvent(SignEvent.deleteAccount(reasons))
    }

    fun getWithdrawReasons(): MutableMap<String, Any> {
        val reasons: MutableMap<String, Any> =
            selectedReasons.value.associate { it.name to it.reason }.toMutableMap()
        if (isOtherReasonSelected.value)
            reasons["SUBJECTIVE_ISSUE"] = otherReason.value
        return reasons
    }
}
