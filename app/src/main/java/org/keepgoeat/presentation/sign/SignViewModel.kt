package org.keepgoeat.presentation.sign

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.keepgoeat.data.datasource.local.KGEDataSource
import org.keepgoeat.data.model.request.RequestAuth
import org.keepgoeat.domain.model.AccountInfo
import org.keepgoeat.domain.repository.AuthRepository
import org.keepgoeat.domain.type.SignType
import org.keepgoeat.presentation.base.viewmodel.MixpanelViewModel
import org.keepgoeat.presentation.type.LoginPlatformType
import org.keepgoeat.util.UiState
import org.keepgoeat.util.mixpanel.SignEvent
import javax.inject.Inject

@HiltViewModel
class SignViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val localStorage: KGEDataSource,
) : MixpanelViewModel() {
    private val _loginUiState = MutableStateFlow<UiState<Pair<Boolean, Boolean>>>(UiState.Loading)
    val loginUiState get() = _loginUiState.asStateFlow()

    fun login(loginPlatForm: LoginPlatformType, accessToken: String) {
        localStorage.loginPlatform = loginPlatForm
        viewModelScope.launch {
            authRepository.login(
                RequestAuth(accessToken, loginPlatForm.name)
            ).onSuccess {
                _loginUiState.value = UiState.Success(
                    Pair(it?.signType == SignType.SIGN_UP, localStorage.isClickedOnboardingButton)
                )
                sendSignEventLog(it?.signType, loginPlatForm.label)
            }.onFailure {
                _loginUiState.value = UiState.Error(it.message)
            }
        }
    }

    fun saveAccount(accountInfo: AccountInfo) {
        localStorage.userName = accountInfo.name
        localStorage.userEmail = accountInfo.email
        when (val state = loginUiState.value) {
            is UiState.Success -> {
                sendUserEventLog(state.data.first)
            }
            else -> {}
        }
    }

    private fun sendUserEventLog(isSignType: Boolean) {
        if (isSignType) {
            mixpanelProvider.setUser()
        }
    }

    private fun sendSignEventLog(signType: SignType?, platform: String) {
        when (signType) {
            SignType.SIGN_UP -> {
                mixpanelProvider.sendEvent(SignEvent.completeSignUp(platform))
            }
            SignType.SIGN_IN -> {
                mixpanelProvider.sendEvent(SignEvent.completeLogin())
            }
            else -> {}
        }
    }
}
