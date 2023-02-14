package org.keepgoeat.data.repository

import org.keepgoeat.data.datasource.local.KGEDataSource
import org.keepgoeat.data.datasource.remote.AuthDataSource
import org.keepgoeat.data.model.request.RequestAuth
import org.keepgoeat.data.model.response.ResponseAuth
import org.keepgoeat.data.model.response.ResponseRefresh
import org.keepgoeat.data.model.response.ResponseWithdraw
import org.keepgoeat.domain.repository.AuthRepository
import timber.log.Timber
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authDataSource: AuthDataSource,
    private val localStorage: KGEDataSource,
) : AuthRepository {
    override suspend fun login(requestAuth: RequestAuth): Result<ResponseAuth.ResponseAuthData?> =
        runCatching {
            authDataSource.login(requestAuth).data
        }.onSuccess {
            with(localStorage) {
                isLogin = true
                it?.let {
                    accessToken = it.accessToken
                    refreshToken = it.refreshToken
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
            localStorage.clear()
        }.onFailure {
            Timber.e(it.message)
        }


    // TODO 코드 정리 필요
    override suspend fun refresh(): Result<ResponseRefresh.ResponseToken?> = runCatching {
        authDataSource.refresh().data
    }
}
