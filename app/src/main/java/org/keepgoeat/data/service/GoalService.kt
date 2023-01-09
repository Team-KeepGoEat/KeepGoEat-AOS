package org.keepgoeat.data.service

import org.keepgoeat.data.model.response.ResponseHome
import retrofit2.Response
import retrofit2.http.GET

interface GoalService {
    @GET("home")
    suspend fun fetchHomeEntireData(): Response<ResponseHome>
}
