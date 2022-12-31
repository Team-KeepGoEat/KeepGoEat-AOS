package org.keepgoeat.data.service

import org.keepgoeat.data.model.request.RequestData
import org.keepgoeat.data.model.response.ResponseResult
import retrofit2.http.Body
import retrofit2.http.POST

interface DummyService {
    @POST("api/dummy")
    suspend fun uploadDummy(@Body request: RequestData): ResponseResult
}
