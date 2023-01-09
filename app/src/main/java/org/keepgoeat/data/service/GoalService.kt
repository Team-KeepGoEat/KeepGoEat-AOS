package org.keepgoeat.data.service

import org.keepgoeat.data.model.request.RequestGoalContent
import org.keepgoeat.data.model.response.ResponseGoalContent
import org.keepgoeat.data.model.response.ResponseHome
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface GoalService {
    @GET("home")
    suspend fun fetchHomeEntireData(): Response<ResponseHome>

    @POST("goal")
    suspend fun uploadGoalContent(@Body request: RequestGoalContent): Response<ResponseGoalContent>
}
