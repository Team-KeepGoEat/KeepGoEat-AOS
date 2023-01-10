package org.keepgoeat.data.service

import org.keepgoeat.data.model.request.RequestGoalContent
import org.keepgoeat.data.model.response.ResponseGoalContent
import org.keepgoeat.data.model.response.ResponseGoalDetail
import org.keepgoeat.data.model.response.ResponseHome
import retrofit2.Response
import retrofit2.http.*

interface GoalService {
    @GET("home")
    suspend fun fetchHomeEntireData(): Response<ResponseHome>

    @POST("goal")
    suspend fun uploadGoalContent(@Body request: RequestGoalContent): Response<ResponseGoalContent>

    @FormUrlEncoded
    @POST("goal/{goalId}")
    suspend fun editGoalContent(@Path("goalId") id: Int, @Field("goalContent") goalContent: String): Response<ResponseGoalContent>

    @GET("history/{goalId}")
    suspend fun fetchGoalDetail(@Path("goalId") goalId: Int): Response<ResponseGoalDetail>
}
