package org.keepgoeat.data.service

import android.content.Context
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

    fun loginKakao(loginListener: (() -> Unit)? = null) {
        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            error?.let(::handleLoginError)
            token?.let(::handleLoginSuccess)
        }
        if (isKakaoTalkLoginAvailable)
            loginByKakaotalk(loginListener, callback)
        else
            loginByKakaoAccount(loginListener, callback)
    }

    fun loginByKakaotalk(loginListener: (() -> Unit)? = null, callback: (OAuthToken?, Throwable?) -> Unit) {
        client.loginWithKakaoTalk(context, callback = callback)
    }

    fun loginByKakaoAccount(loginListener: (() -> Unit)? = null, callback: (OAuthToken?, Throwable?) -> Unit) {
        client.loginWithKakaoAccount(context, callback = callback)
    }

    private fun handleLoginError(throwable: Throwable) {
        val kakaoType = if (isKakaoTalkLoginAvailable) "카카오톡" else "카카오계정"
        Timber.d("$kakaoType 으로 로그인 실패")
    }

    private fun handleLoginSuccess(oAuthToken: OAuthToken) {
        client.me { user, _ ->
            // TODO 로그인 Api 연결
        }
    }

    fun logout() {
        client.logout(Timber::e)
    }
}
