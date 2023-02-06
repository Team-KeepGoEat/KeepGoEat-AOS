package org.keepgoeat.domain.repository

import kotlinx.coroutines.flow.Flow
import org.keepgoeat.data.ApiResult
import org.keepgoeat.data.model.response.*

interface GoalRepository {
    suspend fun achieveGoal(
        goalId: Int,
        isAchieved: Boolean
    ): ResponseGoalAchievement.ResponseGoalAchievementData?

    suspend fun uploadGoalContent(
        title: String,
        isMore: Boolean
    ): ResponseGoalContent.ResponseGoalContentData?

    suspend fun editGoalContent(
        id: Int,
        title: String
    ): ResponseGoalContent.ResponseGoalContentData?

    suspend fun fetchHomeEntireData(): Flow<ApiResult<ResponseHome.HomeData?>>
    suspend fun fetchGoalDetail(goalId: Int): ResponseGoalDetail.ResponseGoalDetailData?
    suspend fun keepGoal(id: Int): ResponseGoalKeep.ResponseGoalKeepData?
    suspend fun deleteGoal(id: Int): ResponseGoalDeleted.ResponseGoalDeletedData?
}
