package org.keepgoeat.domain.repository

import org.keepgoeat.data.model.response.*

interface GoalRepository {
    suspend fun fetchHomeEntireData(): ResponseHome.HomeData?
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

    suspend fun fetchGoalDetail(goalId: Int): ResponseGoalDetail.ResponseGoalDetailData?
    suspend fun keepGoal(id: Int): ResponseGoalKeep.ResponseGoalKeepData?
    suspend fun deleteGoal(id: Int): ResponseGoalDeleted.ResponseGoalDeletedData?
}
