package org.keepgoeat.data.datasource.remote

import org.keepgoeat.data.model.request.RequestGoalAchievement
import org.keepgoeat.data.model.request.RequestGoalContent
import org.keepgoeat.data.model.request.RequestEditedGoalContent
import org.keepgoeat.data.model.response.*
import org.keepgoeat.data.service.GoalService
import javax.inject.Inject

class GoalDataSource @Inject constructor(
    private val goalService: GoalService,
) {
    suspend fun fetchHomeContent(): ResponseHomeContent =
        goalService.fetchHomeContent()

    suspend fun fetchGoalDetail(id: Int): ResponseGoalDetail = goalService.fetchGoalDetail(id)

    suspend fun fetchArchivedGoal(sortType: String): ResponseArchivedGoal =
        goalService.fetchArchivedGoal(sortType)

    suspend fun uploadGoalContent(content: RequestGoalContent): ResponseGoalContent =
        goalService.uploadGoalContent(content)

    suspend fun editGoalContent(
        id: Int,
        content: RequestEditedGoalContent,
    ): ResponseGoalContent = goalService.editGoalContent(id, content)

    suspend fun keepGoal(id: Int): ResponseGoalKeep = goalService.keepGoal(id)

    suspend fun achievedGoal(
        id: Int,
        requestGoalAchievement: RequestGoalAchievement
    ): ResponseGoalAchievement =
        goalService.achieveGoal(id, requestGoalAchievement)

    suspend fun deleteGoal(
        id: Int
    ): ResponseGoalDeleted = goalService.deleteGoal(id)
}
