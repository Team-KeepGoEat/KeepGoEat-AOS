package org.keepgoeat.data.repository

import org.keepgoeat.data.datasource.remote.GoalDataSource
import org.keepgoeat.data.model.request.RequestGoalAchievement
import org.keepgoeat.data.model.request.RequestGoalContent
import org.keepgoeat.data.model.request.RequestGoalContentTitle
import org.keepgoeat.data.model.response.*
import org.keepgoeat.domain.model.GoalDetail
import org.keepgoeat.domain.model.AchievedGoal
import org.keepgoeat.domain.model.HomeContent
import org.keepgoeat.domain.repository.GoalRepository
import javax.inject.Inject

class GoalRepositoryImpl @Inject constructor(
    private val goalDataSource: GoalDataSource,
) : GoalRepository {
    override suspend fun fetchHomeEntireData(): Result<HomeContent> = runCatching {
        goalDataSource.fetchHomeEntireData().data.toHomeGoal()
    }

    override suspend fun achieveGoal(
        goalId: Int,
        isAchieved: Boolean,
    ): Result<ResponseGoalAchievement.ResponseGoalAchievementData> = runCatching {
        goalDataSource.achievedGoal(goalId, RequestGoalAchievement(isAchieved)).data
    }

    override suspend fun uploadGoalContent(
        food: String,
        criterion: String,
        isMore: Boolean,
    ): Result<Int> =
        runCatching {
            goalDataSource.uploadGoalContent(RequestGoalContent(food, criterion, isMore)).data.id
        }

    override suspend fun editGoalContent(
        id: Int,
        food: String,
        criterion: String
    ): Result<Int> = runCatching {
        goalDataSource.editGoalContent(id, RequestGoalContentTitle(food, criterion)).data.id
    }

    override suspend fun fetchGoalDetail(goalId: Int): Result<GoalDetail> =
        runCatching {
            goalDataSource.fetchGoalDetail(goalId).data.toGoalDetail()
        }

    override suspend fun fetchAchievedGoal(sortType: String): Result<List<AchievedGoal>> =
        runCatching {
            goalDataSource.fetchAchievedGoal(sortType).data.toMyGoal()
        }

    override suspend fun keepGoal(id: Int): Result<ResponseGoalKeep.ResponseGoalKeepData> =
        runCatching {
            goalDataSource.keepGoal(id).data
        }

    override suspend fun deleteGoal(id: Int): Result<ResponseGoalDeleted.ResponseGoalDeletedData> =
        runCatching {
            goalDataSource.deleteGoal(id).data
        }
}
