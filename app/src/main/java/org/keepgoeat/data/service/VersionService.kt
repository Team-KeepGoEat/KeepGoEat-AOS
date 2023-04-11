package org.keepgoeat.data.service

import org.keepgoeat.data.model.response.ResponseVersion
import retrofit2.http.GET
import retrofit2.http.Query

interface VersionService {
    @GET("ver")
    suspend fun fetchVersion(
        @Query("client") value: String,
    ): ResponseVersion
}
