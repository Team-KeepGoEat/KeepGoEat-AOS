package org.keepgoeat.data.service

import org.keepgoeat.data.model.request.RequestGoalAchievement
import org.keepgoeat.data.model.request.RequestGoalContent
import org.keepgoeat.data.model.request.RequestGoalContentTitle
import org.keepgoeat.data.model.response.*
import retrofit2.http.*

interface GoalService {
    @GET("home")
    suspend fun fetchHomeEntireData(): ResponseHome

    @POST("goal")
    suspend fun uploadGoalContent(@Body request: RequestGoalContent): ResponseGoalContent

    @POST("goal/{goalId}")
    suspend fun editGoalContent(
        @Path("goalId") id: Int,
        @Body goalContent: RequestGoalContentTitle
    ): ResponseGoalContent

    @GET("history/{goalId}")
    suspend fun fetchGoalDetail(@Path("goalId") goalId: Int): ResponseGoalDetail

    @GET("goal/kept")
    suspend fun fetchAchievedGoal(
        @Query("sort") value: String,
    ): ResponseAchievedGoal

    @POST("goal/keep/{goalId}")
    suspend fun keepGoal(@Path("goalId") id: Int): ResponseGoalKeep

    @POST("goal/achieve/{goalId}")
    suspend fun achieveGoal(
        @Path("goalId") id: Int,
        @Body goalAchievement: RequestGoalAchievement
    ): ResponseGoalAchievement

    @DELETE("goal/{goalId}")
    suspend fun deleteGoal(
        @Path("goalId") id: Int
    ): ResponseGoalDeleted
}
