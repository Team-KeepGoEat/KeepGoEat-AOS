package org.keepgoeat.data.service

import org.keepgoeat.data.model.response.ResponseUserInfo
import retrofit2.http.GET

interface UserService {
    @GET("mypage")
    suspend fun fetchUserInfo(): ResponseUserInfo
}
