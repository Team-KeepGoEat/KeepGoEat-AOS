package org.keepgoeat.data.service

import org.keepgoeat.data.model.response.ResponseHome
import retrofit2.Response
import retrofit2.http.GET

interface HomeService {
    @GET("home")
    suspend fun fetchHomeEntire(): Response<ResponseHome>
}
