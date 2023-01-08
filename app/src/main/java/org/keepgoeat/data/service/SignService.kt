package org.keepgoeat.data.service

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import dagger.hilt.android.qualifiers.ActivityContext
import timber.log.Timber
import javax.inject.Inject

class SignService @Inject constructor(
    @ActivityContext private val context: Context,
    private val client: UserApiClient
) {
    val isKakaoTalkLoginAvailable: Boolean
        get() = client.isKakaoTalkLoginAvailable(context)
    private val _loginState = MutableLiveData<LoginState>(LoginState.Init)
    val loginState: LiveData<LoginState> get() = _loginState

    private val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        error?.let(::handleLoginError)
        token?.let(::handleLoginSuccess)
    }

    fun loginByKakaotalk() {
        client.loginWithKakaoTalk(context, callback = callback)
    }

    fun loginByKakaoAccount() {
        client.loginWithKakaoAccount(context, callback = callback)
    }

    private fun handleLoginError(throwable: Throwable) {
        _loginState.value = LoginState.Failure(throwable)
    }

    private fun handleLoginSuccess(oAuthToken: OAuthToken) {
        client.me { user, _ ->
            _loginState.value = LoginState.Success(oAuthToken.accessToken, user?.id.toString())
        }
    }

    fun logout() {
        client.logout(Timber::e)
    }

    sealed class LoginState {
        object Init : LoginState()
        data class Success(val token: String, val id: String) : LoginState()
        data class Failure(val error: Throwable) : LoginState()
    }
}
