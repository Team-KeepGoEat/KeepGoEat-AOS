package org.keepgoeat.data.datasource.remote

import org.keepgoeat.data.ApiResult
import org.keepgoeat.data.model.request.RequestAuth
import org.keepgoeat.data.model.response.ResponseAuth
import org.keepgoeat.data.model.response.ResponseRefresh
import org.keepgoeat.data.service.AuthService
import org.keepgoeat.util.safeApiCall
import javax.inject.Inject

class AuthDataSource @Inject constructor(
    private val authService: AuthService
) {
    suspend fun login(requestAuth: RequestAuth): ApiResult<ResponseAuth?> =
        safeApiCall { authService.login(requestAuth) }

    suspend fun refresh(): ApiResult<ResponseRefresh?> =
        safeApiCall { authService.refresh() }
}
