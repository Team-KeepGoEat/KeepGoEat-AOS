package org.keepgoeat.data.datasource.remote

import org.keepgoeat.data.datasource.local.KGEDataSource
import org.keepgoeat.data.model.request.RequestAuth
import org.keepgoeat.data.model.response.ResponseAuth
import org.keepgoeat.data.model.response.ResponseRefresh
import org.keepgoeat.data.model.response.ResponseWithdraw
import org.keepgoeat.data.service.AuthService
import javax.inject.Inject

class AuthDataSource @Inject constructor(
    private val authService: AuthService,
    private val kgeDataSource: KGEDataSource,
) {
    suspend fun login(requestAuth: RequestAuth): ResponseAuth = authService.login(requestAuth)

    suspend fun refresh(): ResponseRefresh = authService.refresh()

    suspend fun deleteAccount(): ResponseWithdraw =
        authService.deleteAccount(kgeDataSource.loginPlatform.name)
}
