package org.keepgoeat.domain.repository

import org.keepgoeat.data.model.response.ResponseGoalAchievement
import org.keepgoeat.data.model.response.ResponseGoalDeleted
import org.keepgoeat.data.model.response.ResponseGoalKeep
import org.keepgoeat.data.model.response.ResponseHome
import org.keepgoeat.domain.model.GoalDetail

interface GoalRepository {
    suspend fun achieveGoal(
        goalId: Int,
        isAchieved: Boolean
    ): Result<ResponseGoalAchievement.ResponseGoalAchievementData>

    suspend fun uploadGoalContent(
        title: String,
        isMore: Boolean
    ): Result<Int>

    suspend fun editGoalContent(
        id: Int,
        title: String
    ): Result<Int>

    suspend fun fetchHomeEntireData(): Result<ResponseHome.HomeData>
    suspend fun fetchGoalDetail(goalId: Int): Result<GoalDetail>
    suspend fun keepGoal(id: Int): Result<ResponseGoalKeep.ResponseGoalKeepData>
    suspend fun deleteGoal(id: Int): Result<ResponseGoalDeleted.ResponseGoalDeletedData>
}
