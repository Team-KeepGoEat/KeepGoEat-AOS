package org.keepgoeat.data.repository

import org.keepgoeat.data.ApiResult
import org.keepgoeat.data.datasource.local.KGEDataSource
import org.keepgoeat.data.datasource.remote.AuthDataSource
import org.keepgoeat.data.model.request.RequestAuth
import org.keepgoeat.data.model.response.ResponseAuth
import org.keepgoeat.domain.repository.AuthRepository
import timber.log.Timber
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authDataSource: AuthDataSource,
    private val localStorage: KGEDataSource
) : AuthRepository {
    override suspend fun login(requestAuth: RequestAuth): ResponseAuth.ResponseAuthData? {
        val result = authDataSource.login(requestAuth)
        return when (result) {
            is ApiResult.Success -> {
                val response = result.data?.data
                with(localStorage) {
                    isLogin = true
                    response?.let {
                        accestToken = it.accessToken
                        refreshToken = it.refreshToken
                    }
                }
                Timber.d(result.data?.message)
                result.data?.data
            }
            is ApiResult.NetworkError -> {
                Timber.d("Network Error")
                null
            }
            is ApiResult.GenericError -> {
                Timber.d("(${result.code}): ${result.message}")
                null
            }
        }
    }
}
