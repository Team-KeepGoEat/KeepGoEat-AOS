package org.keepgoeat.data.service

import android.content.Context
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import dagger.hilt.android.qualifiers.ActivityContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.keepgoeat.data.datasource.local.KGEDataSource
import org.keepgoeat.data.model.request.RequestAuth
import org.keepgoeat.domain.repository.AuthRepository
import timber.log.Timber
import javax.inject.Inject

class KakaoAuthService @Inject constructor(
    @ActivityContext private val context: Context,
    private val client: UserApiClient,
    private val authRepository: AuthRepository,
    private val localStorage: KGEDataSource,
) {
    private val isKakaoTalkLoginAvailable: Boolean
        get() = client.isKakaoTalkLoginAvailable(context)

    fun loginKakao(loginListener: ((Boolean, Boolean) -> Unit)) {
        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) handleLoginError(error)
            else if (token != null) handleLoginSuccess(token, loginListener)
        }

        if (isKakaoTalkLoginAvailable) client.loginWithKakaoTalk(context, callback = callback)
        else client.loginWithKakaoAccount(context, callback = callback)
    }

    private fun handleLoginError(throwable: Throwable) {
        val kakaoType = if (isKakaoTalkLoginAvailable) "카카오톡" else "카카오 계정"
        Timber.d("${kakaoType}으로 로그인 실패 (${throwable.message})")
    }

    private fun handleLoginSuccess(
        oAuthToken: OAuthToken,
        loginListener: ((Boolean, Boolean) -> Unit),
    ) {
        client.me { user, _ ->
            CoroutineScope(Dispatchers.Main).launch {
                val result = withContext(Dispatchers.IO) {
                    authRepository.login(RequestAuth(oAuthToken.accessToken, PLATFORM_KAKAO))
                }
                Timber.d(oAuthToken.accessToken)
                result?.let {
                    loginListener(
                        result.getOrThrow()?.type == SIGN_UP,
                        localStorage.isClickedOnboardingButton
                    )
                }
            }
        }
    }

    fun logoutKakao() {
        client.logout { error ->
            if (error == null) {
                Timber.i("로그아웃 성공. SDK에서 토큰 삭제됨")
                localStorage.clear()
            } else {
                Timber.e("로그아웃 실패. SDK에서 토큰 삭제됨($error)")
            }
        }
    }

    fun unlinkKakao() {
        client.unlink { error ->
            if (error == null) {
                Timber.d("연결 끊기 성공. SDK에서 토큰 삭제 됨")
                // TODO 탈퇴 api 호출
                localStorage.clear()
            } else {
                Timber.e("연결 끊기 실패($error)")
            }
        }
    }

    companion object {
        private const val PLATFORM_KAKAO = "KAKAO"
        private const val SIGN_UP = "signup"
    }
}
