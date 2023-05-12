package org.keepgoeat.data.service

import android.content.Context
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.NidOAuthLogin
import com.navercorp.nid.oauth.OAuthLoginCallback
import com.navercorp.nid.profile.NidProfileCallback
import com.navercorp.nid.profile.data.NidProfileResponse
import dagger.hilt.android.qualifiers.ActivityContext
import org.keepgoeat.BuildConfig
import org.keepgoeat.domain.model.AccountInfo
import org.keepgoeat.presentation.type.LoginPlatformType
import timber.log.Timber
import javax.inject.Inject

class NaverAuthService @Inject constructor(
    @ActivityContext private val context: Context,
) {
    init {
        NaverIdLoginSDK.initialize(
            context, BuildConfig.NAVER_CLIENT_ID, BuildConfig.NAVER_CLIENT_SECRETE,
            CLIENT_NAME
        )
    }

    fun loginNaver(
        loginListener: ((LoginPlatformType, String) -> Unit),
        accountListener: (AccountInfo) -> Unit
    ) {
        val oauthLoginCallback = object : OAuthLoginCallback {
            override fun onSuccess() {
                val accessToken = requireNotNull(NaverIdLoginSDK.getAccessToken())
                Timber.d(accessToken)
                loginListener(LoginPlatformType.NAVER, accessToken)
                getAccountInfo(accountListener)
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

    fun getAccountInfo(accountListener: (AccountInfo) -> Unit) {
        NidOAuthLogin().callProfileApi(object : NidProfileCallback<NidProfileResponse> {
            override fun onSuccess(result: NidProfileResponse) {
                accountListener(
                    AccountInfo(
                        result.profile?.name ?: "",
                        result.profile?.email ?: ""
                    )
                )
            }

            override fun onFailure(httpStatus: Int, message: String) {
                Timber.i("Error : $httpStatus, message : $message")
            }

            override fun onError(errorCode: Int, message: String) {
                onFailure(errorCode, message)
            }
        })
    }

    fun logoutNaver(logoutListener: (() -> Unit)) {
        NaverIdLoginSDK.logout()
        logoutListener()
    }

    fun deleteAccountNaver(deleteAccountListener: (() -> Unit)) {
        NidOAuthLogin().callDeleteTokenApi(
            context,
            object : OAuthLoginCallback {
                override fun onSuccess() {
                    Timber.d("연결 끊기 성공. SDK에서 토큰 삭제 됨")
                    deleteAccountListener()
                }

                override fun onFailure(httpStatus: Int, message: String) {
                    Timber.d("errorCode: ${NaverIdLoginSDK.getLastErrorCode().code}")
                    Timber.d("errorDesc: ${NaverIdLoginSDK.getLastErrorDescription()}")
                }

                override fun onError(errorCode: Int, message: String) {
                    onFailure(errorCode, message)
                }
            }
        )
    }

    companion object {
        private const val CLIENT_NAME = "킵고잇"
    }
}
