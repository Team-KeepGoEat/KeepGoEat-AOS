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
                val isSignUp = it?.signType == SignType.SIGN_UP
                _loginUiState.value = UiState.Success(
                    Pair(isSignUp, localStorage.isClickedOnboardingButton)
                )
                initMixpanelGoalNumber(isSignUp)
                sendSignEventLog(it?.signType, loginPlatForm.label)
            }.onFailure {
                _loginUiState.value = UiState.Error(it.message)
            }
        }
    }

    fun saveAccount(accountInfo: AccountInfo) {
        localStorage.userName = accountInfo.name
        localStorage.userEmail = accountInfo.email
        mixpanelProvider.setUserProfile()
    }

    /** 회원가입 시에만 유저 프로퍼티 > 목표수를 초기화하는 함수 */
    private fun initMixpanelGoalNumber(isSignUp: Boolean) {
        if (isSignUp) mixpanelProvider.initUserGoalNumber()
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
