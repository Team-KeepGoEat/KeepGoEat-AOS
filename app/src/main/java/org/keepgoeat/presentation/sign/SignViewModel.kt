package org.keepgoeat.presentation.sign

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.keepgoeat.data.datasource.local.KGEDataSource
import org.keepgoeat.data.model.request.RequestAuth
import org.keepgoeat.domain.model.AccountInfo
import org.keepgoeat.domain.repository.AuthRepository
import org.keepgoeat.presentation.type.SocialLoginType
import org.keepgoeat.util.UiState
import javax.inject.Inject

@HiltViewModel
class SignViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val localStorage: KGEDataSource,
) :
    ViewModel() {
    private var _loginUiState = MutableStateFlow<UiState<Pair<Boolean, Boolean>>>(UiState.Loading)
    val loginUiState get() = _loginUiState.asStateFlow()

    fun login(loginPlatForm: SocialLoginType, accessToken: String) {
        localStorage.loginPlatform = loginPlatForm
        viewModelScope.launch {
            authRepository.login(
                RequestAuth(accessToken, loginPlatForm.name)
            ).onSuccess {
                _loginUiState.value = UiState.Success(
                    Pair(it?.type == SIGN_UP, localStorage.isClickedOnboardingButton)
                )
            }.onFailure {
                _loginUiState.value = UiState.Error(it.message)
            }
        }
    }

    fun saveAccount(accountInfo: AccountInfo) {
        localStorage.userName = accountInfo.name
        localStorage.userEmail = accountInfo.email
    }

    companion object {
        private const val SIGN_UP = "signup"
    }
}
