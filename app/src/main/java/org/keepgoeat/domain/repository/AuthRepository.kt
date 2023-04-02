package org.keepgoeat.domain.repository

import org.keepgoeat.data.model.request.RequestAuth
import org.keepgoeat.data.model.response.ResponseAuth
import org.keepgoeat.data.model.response.ResponseRefresh
import org.keepgoeat.data.model.response.ResponseWithdraw

interface AuthRepository {
    suspend fun login(requestAuth: RequestAuth): Result<ResponseAuth.ResponseAuthData?>
    suspend fun refresh(): Result<ResponseRefresh.ResponseToken?>
    suspend fun deleteAccount(): Result<ResponseWithdraw>
}
