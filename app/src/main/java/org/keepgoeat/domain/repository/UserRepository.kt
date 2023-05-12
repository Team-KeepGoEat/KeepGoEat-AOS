package org.keepgoeat.domain.repository

import org.keepgoeat.domain.model.UserInfo

interface UserRepository {
    suspend fun fetchUserInfo(): Result<UserInfo?>
}
