package org.keepgoeat.data.service

import android.content.Context
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.NidOAuthLogin
import com.navercorp.nid.oauth.OAuthLoginCallback
import dagger.hilt.android.qualifiers.ActivityContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.keepgoeat.BuildConfig
import org.keepgoeat.data.datasource.local.KGEDataSource
import org.keepgoeat.data.model.request.RequestAuth
import org.keepgoeat.domain.repository.AuthRepository
import org.keepgoeat.presentation.type.SocialLoginType
import timber.log.Timber
import javax.inject.Inject

class NaverAuthService @Inject constructor(
    @ActivityContext private val context: Context,
    private val authRepository: AuthRepository,
    private val localStorage: KGEDataSource,
) {
    init {
        NaverIdLoginSDK.initialize(
            context, BuildConfig.NAVER_CLIENT_ID, BuildConfig.NAVER_CLIENT_SECRETE,
            CLIENT_NAME
        )
    }

    fun loginNaver(loginListener: ((Boolean, Boolean) -> Unit)) {
        val oauthLoginCallback = object : OAuthLoginCallback {
            override fun onSuccess() {
                val accessToken = requireNotNull(NaverIdLoginSDK.getAccessToken())
                CoroutineScope(Dispatchers.Main).launch {
                    val result = withContext(Dispatchers.IO) {
                        authRepository.login(
                            RequestAuth(accessToken, PLATFORM_NAVER)
                        )
                    }
                    Timber.d(accessToken)
                    localStorage.loginPlatform = SocialLoginType.NAVER // TODO 리팩토링 필요
                    loginListener(result.getOrThrow()?.type == SIGN_UP,
                        localStorage.isClickedOnboardingButton)
                }
            }

            override fun onFailure(httpStatus: Int, message: String) {
                Timber.i(NaverIdLoginSDK.getLastErrorCode().code)
                Timber.i(NaverIdLoginSDK.getLastErrorDescription())
            }

            override fun onError(errorCode: Int, message: String) {
                onFailure(errorCode, message)
            }
        }
        NaverIdLoginSDK.authenticate(
            context, oauthLoginCallback
        )
    }

    fun logoutNaver() {
        NaverIdLoginSDK.logout()
        localStorage.clear()
    }

    fun unlinkNaver() {
        NidOAuthLogin().callDeleteTokenApi(context, object : OAuthLoginCallback {
            override fun onSuccess() {
                // TODO 탈퇴 api 호출
                localStorage.clear()
            }

            override fun onFailure(httpStatus: Int, message: String) {
                Timber.d("errorCode: ${NaverIdLoginSDK.getLastErrorCode().code}")
                Timber.d("errorDesc: ${NaverIdLoginSDK.getLastErrorDescription()}")
            }

            override fun onError(errorCode: Int, message: String) {
                onFailure(errorCode, message)
            }
        })
    }

    companion object {
        private const val CLIENT_NAME = "킵고잇"
        private const val PLATFORM_NAVER = "NAVER"
        private const val SIGN_UP = "signup"
    }
}
