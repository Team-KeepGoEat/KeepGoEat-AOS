package org.keepgoeat.domain.repository

import org.keepgoeat.data.model.request.RequestAuth
import org.keepgoeat.data.model.response.ResponseRefresh
import org.keepgoeat.data.model.response.ResponseWithdraw
import org.keepgoeat.domain.model.AuthInfo

interface AuthRepository {
    suspend fun login(requestAuth: RequestAuth): Result<AuthInfo?>
    suspend fun refresh(): Result<ResponseRefresh.ResponseToken?>
    suspend fun deleteAccount(): Result<ResponseWithdraw>
}
