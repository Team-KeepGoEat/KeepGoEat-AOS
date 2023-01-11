package org.keepgoeat.domain.repository

import org.keepgoeat.data.model.request.RequestAuth
import org.keepgoeat.data.model.response.ResponseAuth

interface AuthRepository {
    suspend fun login(requestAuth: RequestAuth): ResponseAuth.ResponseAuthData?
}