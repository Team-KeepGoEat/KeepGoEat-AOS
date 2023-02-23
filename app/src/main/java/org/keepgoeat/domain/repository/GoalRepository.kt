package org.keepgoeat.domain.repository

import org.keepgoeat.data.model.response.*
import org.keepgoeat.domain.model.GoalDetail
import org.keepgoeat.domain.model.AchievedGoal

interface GoalRepository {
    suspend fun achieveGoal(
        goalId: Int,
        isAchieved: Boolean
    ): Result<ResponseGoalAchievement.ResponseGoalAchievementData>

    suspend fun uploadGoalContent(
        food: String,
        criterion: String,
        isMore: Boolean
    ): Result<Int>

    suspend fun editGoalContent(
        id: Int,
        food: String,
        criterion: String
    ): Result<Int>

    suspend fun fetchHomeEntireData(): Result<ResponseHome.HomeData>
    suspend fun fetchGoalDetail(goalId: Int): Result<GoalDetail>
    suspend fun fetchAchievedGoal(sortType: String): Result<List<AchievedGoal>>
    suspend fun keepGoal(id: Int): Result<ResponseGoalKeep.ResponseGoalKeepData>
    suspend fun deleteGoal(id: Int): Result<ResponseGoalDeleted.ResponseGoalDeletedData>
}
