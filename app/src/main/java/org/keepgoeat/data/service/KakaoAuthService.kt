package org.keepgoeat.data.service

import android.content.Context
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import dagger.hilt.android.qualifiers.ActivityContext
import org.keepgoeat.domain.model.AccountInfo
import org.keepgoeat.presentation.type.LoginPlatformType
import timber.log.Timber
import javax.inject.Inject

class KakaoAuthService @Inject constructor(
    @ActivityContext private val context: Context,
    private val client: UserApiClient,
) {
    private val isKakaoTalkLoginAvailable: Boolean
        get() = client.isKakaoTalkLoginAvailable(context)

    fun loginKakao(
        loginListener: ((LoginPlatformType, String) -> Unit),
        accountListener: ((AccountInfo) -> Unit)
    ) {
        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) handleLoginError(error)
            else if (token != null) handleLoginSuccess(token, loginListener, accountListener)
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
        loginListener: ((LoginPlatformType, String) -> Unit),
        accountListener: ((AccountInfo) -> Unit),
    ) {
        client.me { user, error ->
            Timber.d(oAuthToken.accessToken)
            loginListener(LoginPlatformType.KAKAO, oAuthToken.accessToken)
            if (error != null) Timber.e("kakao 사용자 정보 요청 실패 $error")
            else if (user != null) {
                accountListener(
                    AccountInfo(
                        user.kakaoAccount?.profile?.nickname ?: "",
                        user.kakaoAccount?.email ?: ""
                    )
                )
            }
        }
    }

    fun logoutKakao(logoutListener: (() -> Unit)) {
        client.logout { error ->
            if (error == null) {
                Timber.i("로그아웃 성공. SDK에서 토큰 삭제됨")
                logoutListener()
            } else {
                Timber.e("로그아웃 실패. SDK에서 토큰 삭제됨($error)")
            }
        }
    }

    fun deleteAccountKakao(deleteAccountListener: (() -> Unit)) {
        client.unlink { error ->
            if (error == null) {
                Timber.d("연결 끊기 성공. SDK에서 토큰 삭제 됨")
                deleteAccountListener()
            } else {
                Timber.e("연결 끊기 실패($error)")
            }
        }
    }
}
