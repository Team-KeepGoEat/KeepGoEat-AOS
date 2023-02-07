package org.keepgoeat.data.repository

import org.keepgoeat.data.ApiResult
import org.keepgoeat.data.datasource.remote.GoalDataSource
import org.keepgoeat.data.model.request.RequestGoalAchievement
import org.keepgoeat.data.model.request.RequestGoalContent
import org.keepgoeat.data.model.request.RequestGoalContentTitle
import org.keepgoeat.data.model.response.ResponseGoalAchievement
import org.keepgoeat.data.model.response.ResponseGoalDeleted
import org.keepgoeat.data.model.response.ResponseGoalKeep
import org.keepgoeat.data.model.response.ResponseHome
import org.keepgoeat.domain.model.GoalDetail
import org.keepgoeat.domain.repository.GoalRepository
import timber.log.Timber
import javax.inject.Inject

class GoalRepositoryImpl @Inject constructor(
    private val goalDataSource: GoalDataSource,
) : GoalRepository {
    override suspend fun fetchHomeEntireData(): Result<ResponseHome.HomeData> = runCatching {
        goalDataSource.fetchHomeEntireData().data
    }

    override suspend fun achieveGoal(
        goalId: Int,
        isAchieved: Boolean
    ): ResponseGoalAchievement.ResponseGoalAchievementData? {
        val result = goalDataSource.achievedGoal(goalId, RequestGoalAchievement(isAchieved))
        return when (result) {
            is ApiResult.Success -> {
                result.data?.data
            }
            is ApiResult.NetworkError -> {
                Timber.d("Network Error")
                null
            }
            is ApiResult.GenericError -> {
                Timber.d("(${result.code}): ${result.message}")
                null
            }
        }
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

    override suspend fun keepGoal(id: Int): ResponseGoalKeep.ResponseGoalKeepData? {
        val result = goalDataSource.keepGoal(id)

        return when (result) {
            is ApiResult.Success -> {
                result.data?.data
            }
            is ApiResult.NetworkError -> {
                Timber.d("Network Error")
                null
            }
            is ApiResult.GenericError -> {
                Timber.d("(${result.code}): ${result.message}")
                null
            }
        }
    }

    override suspend fun deleteGoal(id: Int): ResponseGoalDeleted.ResponseGoalDeletedData? {
        val result = goalDataSource.deleteGoal(id)

        return when (result) {
            is ApiResult.Success -> {
                result.data?.data
            }
            is ApiResult.NetworkError -> {
                Timber.d("Network Error")
                null
            }
            is ApiResult.GenericError -> {
                Timber.d("(${result.code}): ${result.message}")
                null
            }
        }
    }
}
