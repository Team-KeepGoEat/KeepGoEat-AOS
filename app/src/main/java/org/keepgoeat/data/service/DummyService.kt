package org.keepgoeat.data.service

import org.keepgoeat.data.model.request.RequestData
import org.keepgoeat.data.model.response.ResponseListResult
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface DummyService {
    @POST("api/dummy")
    suspend fun uploadDummy(@Body request: RequestData): Response<ResponseListResult>
}
