package org.keepgoeat.data.repository

import org.keepgoeat.data.datasource.local.KGEDataSource
import org.keepgoeat.data.datasource.remote.AuthDataSource
import org.keepgoeat.data.model.request.RequestAuth
import org.keepgoeat.data.model.response.ResponseRefresh
import org.keepgoeat.data.model.response.ResponseWithdraw
import org.keepgoeat.domain.model.AuthInfo
import org.keepgoeat.domain.repository.AuthRepository
import org.keepgoeat.domain.type.SignType
import timber.log.Timber
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authDataSource: AuthDataSource,
    private val localStorage: KGEDataSource,
) : AuthRepository {
    override suspend fun login(requestAuth: RequestAuth): Result<AuthInfo?> =
        runCatching {
            authDataSource.login(requestAuth).data?.toAuthInfo()
        }.onSuccess {
            with(localStorage) {
                isLogin = true
                it?.let {
                    accessToken = it.accessToken
                    refreshToken = it.refreshToken

                    if (it.signType == SignType.SIGN_IN)
                        isClickedOnboardingButton = true
                }
            }
        }.onFailure {
            Timber.e(it.message)
        }

    override suspend fun deleteAccount(): Result<ResponseWithdraw> =
        runCatching {
            authDataSource.deleteAccount()
        }.onSuccess {
            Timber.d("회원 탈퇴 성공")
            localStorage.clear(true)
        }.onFailure {
            Timber.e(it.message)
        }

    override suspend fun refresh(): Result<ResponseRefresh.ResponseToken?> =
        runCatching {
            authDataSource.refresh().data
        }
}
