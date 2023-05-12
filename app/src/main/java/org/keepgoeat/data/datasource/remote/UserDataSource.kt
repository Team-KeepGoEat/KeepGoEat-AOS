package org.keepgoeat.data.datasource.remote

import org.keepgoeat.data.model.response.ResponseUserInfo
import org.keepgoeat.data.service.UserService
import javax.inject.Inject

class UserDataSource @Inject constructor(
    private val userService: UserService,
) {
    suspend fun fetchUserInfo(): ResponseUserInfo =
        userService.fetchUserInfo()
}
