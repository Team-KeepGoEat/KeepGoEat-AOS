package org.keepgoeat.data.datasource.remote

import org.keepgoeat.data.ApiResult
import org.keepgoeat.data.model.request.RequestGoalAchievement
import org.keepgoeat.data.model.request.RequestGoalContent
import org.keepgoeat.data.model.request.RequestGoalContentTitle
import org.keepgoeat.data.model.response.*
import org.keepgoeat.data.service.GoalService
import org.keepgoeat.util.safeApiCall
import javax.inject.Inject

class GoalDataSource @Inject constructor(
    private val goalService: GoalService,
) {
    suspend fun fetchHomeEntireData(): ApiResult<ResponseHome?> =
        safeApiCall { goalService.fetchHomeEntireData() }

    suspend fun uploadGoalContent(requestGoalContent: RequestGoalContent): ResponseGoalContent =
        goalService.uploadGoalContent(requestGoalContent)

    suspend fun editGoalContent(
        id: Int,
        title: RequestGoalContentTitle,
    ): ResponseGoalContent = goalService.editGoalContent(id, title)

    suspend fun fetchGoalDetail(id: Int): ResponseGoalDetail = goalService.fetchGoalDetail(id)

    suspend fun keepGoal(id: Int): ResponseGoalKeep = goalService.keepGoal(id)

    suspend fun achievedGoal(
        id: Int,
        requestGoalAchievement: RequestGoalAchievement
    ): ApiResult<ResponseGoalAchievement?> =
        safeApiCall { goalService.achieveGoal(id, requestGoalAchievement) }

    suspend fun deleteGoal(
        id: Int
    ): ApiResult<ResponseGoalDeleted?> =
        safeApiCall { goalService.deleteGoal(id) }
}
