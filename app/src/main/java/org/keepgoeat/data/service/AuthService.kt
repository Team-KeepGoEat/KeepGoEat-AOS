package org.keepgoeat.data.service

import org.keepgoeat.data.model.request.RequestAuth
import org.keepgoeat.data.model.response.ResponseAuth
import org.keepgoeat.data.model.response.ResponseRefresh
import org.keepgoeat.data.model.response.ResponseWithdraw
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface AuthService {
    @POST("auth")
    suspend fun login(@Body authInfo: RequestAuth): ResponseAuth

    @GET("auth/refresh")
    suspend fun refresh(): ResponseRefresh

    @GET("auth/withdraw")
    suspend fun deleteAccount(@Query("code") platform: String): ResponseWithdraw
}
