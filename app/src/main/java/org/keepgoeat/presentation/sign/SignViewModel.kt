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
import org.keepgoeat.presentation.type.LoginPlatformType
import org.keepgoeat.util.UiState
import org.keepgoeat.util.mixpanel.MixpanelProvider
import org.keepgoeat.util.mixpanel.SignEvent
import javax.inject.Inject

@HiltViewModel
class SignViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val localStorage: KGEDataSource,
    private val mixpanelProvider: MixpanelProvider,
) :
    ViewModel() {
    private var _loginUiState = MutableStateFlow<UiState<Pair<Boolean, Boolean>>>(UiState.Loading)
    val loginUiState get() = _loginUiState.asStateFlow()

    fun login(loginPlatForm: LoginPlatformType, accessToken: String) {
        localStorage.loginPlatform = loginPlatForm
        viewModelScope.launch {
            authRepository.login(
                RequestAuth(accessToken, loginPlatForm.name)
            ).onSuccess {
                _loginUiState.value = UiState.Success(
                    Pair(it?.type == SIGN_UP, localStorage.isClickedOnboardingButton)
                )
                sendSignEventLog(it?.type, loginPlatForm)
            }.onFailure {
                _loginUiState.value = UiState.Error(it.message)
            }
        }
    }

    fun saveAccount(accountInfo: AccountInfo) {
        localStorage.userName = accountInfo.name
        localStorage.userEmail = accountInfo.email
    }

    private fun sendSignEventLog(signType: String?, platform: LoginPlatformType) {
        when (signType) {
            SIGN_UP -> {
                mixpanelProvider.setUser()
                mixpanelProvider.sendEvent(SignEvent.completeSignUp(platform))
            }
            SIGN_IN -> {
                mixpanelProvider.sendEvent(SignEvent.completeLogin())
            }
        }
    }

    companion object {
        private const val SIGN_UP = "signup"
        private const val SIGN_IN = "signin"
    }
}
