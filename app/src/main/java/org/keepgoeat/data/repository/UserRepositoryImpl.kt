package org.keepgoeat.data.repository

import org.keepgoeat.data.datasource.remote.UserDataSource
import org.keepgoeat.domain.model.UserInfo
import org.keepgoeat.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userDataSource: UserDataSource,
) : UserRepository {
    override suspend fun fetchUserInfo(): Result<UserInfo?> =
        runCatching {
            userDataSource.fetchUserInfo().data?.toUserInfo()
        }
}
