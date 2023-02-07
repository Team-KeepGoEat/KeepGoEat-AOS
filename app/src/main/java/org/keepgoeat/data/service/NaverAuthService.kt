package org.keepgoeat.data.service

import android.content.Context
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.OAuthLoginCallback
import dagger.hilt.android.qualifiers.ActivityContext
import org.keepgoeat.BuildConfig
import org.keepgoeat.data.datasource.local.KGEDataSource
import org.keepgoeat.domain.repository.AuthRepository
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
                // TODO: 네이버 로그인 성공 시 로직 처리
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

    companion object {
        private const val CLIENT_NAME = "킵고잇"
    }
}
