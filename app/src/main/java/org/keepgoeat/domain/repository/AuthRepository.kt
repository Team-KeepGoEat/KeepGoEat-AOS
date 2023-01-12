package org.keepgoeat.domain.repository

import org.keepgoeat.data.model.request.RequestAuth
import org.keepgoeat.data.model.response.ResponseAuth
import org.keepgoeat.data.model.response.ResponseRefresh

interface AuthRepository {
    suspend fun login(requestAuth: RequestAuth): ResponseAuth.ResponseAuthData?

    suspend fun refresh(): ResponseRefresh.Token?
}
