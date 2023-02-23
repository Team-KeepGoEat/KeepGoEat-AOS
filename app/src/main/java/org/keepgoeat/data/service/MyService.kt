package org.keepgoeat.data.service

import org.keepgoeat.data.model.response.ResponseMy
import retrofit2.http.GET
import retrofit2.http.Query

interface MyService {
    @GET("goal/kept")
    suspend fun fetchMyData(
        @Query("sort") value: String,
    ): ResponseMy
}
