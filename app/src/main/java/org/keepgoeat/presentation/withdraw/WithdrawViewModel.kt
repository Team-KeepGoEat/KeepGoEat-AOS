package org.keepgoeat.presentation.withdraw

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.keepgoeat.data.datasource.local.KGEDataSource
import org.keepgoeat.domain.repository.AuthRepository
import org.keepgoeat.util.UiState
import javax.inject.Inject

@HiltViewModel
class WithdrawViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val localStorage: KGEDataSource,
) : ViewModel() {
    val loginPlatForm = localStorage.loginPlatform
    private val _deleteAccountUiState =
        MutableStateFlow<UiState<Boolean>>(UiState.Loading)
    val deleteAccountUiState get() = _deleteAccountUiState.asStateFlow()

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
