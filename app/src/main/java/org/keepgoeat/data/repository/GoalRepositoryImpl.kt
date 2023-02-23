package org.keepgoeat.data.repository

import org.keepgoeat.data.datasource.remote.GoalDataSource
import org.keepgoeat.data.model.request.RequestGoalAchievement
import org.keepgoeat.data.model.request.RequestGoalContent
import org.keepgoeat.data.model.request.RequestGoalContentTitle
import org.keepgoeat.data.model.response.*
import org.keepgoeat.domain.model.GoalDetail
import org.keepgoeat.domain.model.MyGoal
import org.keepgoeat.domain.repository.GoalRepository
import javax.inject.Inject

class GoalRepositoryImpl @Inject constructor(
    private val goalDataSource: GoalDataSource,
) : GoalRepository {
    override suspend fun fetchHomeEntireData(): Result<ResponseHome.HomeData> = runCatching {
        goalDataSource.fetchHomeEntireData().data
    }

    override suspend fun achieveGoal(
        goalId: Int,
        isAchieved: Boolean,
    ): Result<ResponseGoalAchievement.ResponseGoalAchievementData> = runCatching {
        goalDataSource.achievedGoal(goalId, RequestGoalAchievement(isAchieved)).data
    }

    override suspend fun uploadGoalContent(
        title: String,
        isMore: Boolean,
    ): Result<Int> =
        runCatching {
            goalDataSource.uploadGoalContent(RequestGoalContent(title, isMore)).data.id
        }

    override suspend fun editGoalContent(
        id: Int,
        title: String,
    ): Result<Int> = runCatching {
        goalDataSource.editGoalContent(id, RequestGoalContentTitle(title)).data.id
    }

    override suspend fun fetchGoalDetail(goalId: Int): Result<GoalDetail> =
        runCatching {
            goalDataSource.fetchGoalDetail(goalId).data.toGoalDetail()
        }

    override suspend fun fetchAchievedGoal(sortType: String): Result<List<MyGoal>> =
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
