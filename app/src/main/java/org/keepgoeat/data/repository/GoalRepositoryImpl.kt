package org.keepgoeat.data.repository

import org.keepgoeat.data.ApiResult
import org.keepgoeat.data.datasource.remote.GoalDataSource
import org.keepgoeat.data.model.request.RequestGoalCompleted
import org.keepgoeat.data.model.request.RequestGoalContent
import org.keepgoeat.data.model.request.RequestGoalContentTitle
import org.keepgoeat.data.model.response.ResponseCompletedResult
import org.keepgoeat.data.model.response.ResponseGoalContent
import org.keepgoeat.data.model.response.ResponseGoalDetail
import org.keepgoeat.data.model.response.ResponseGoalKeep
import org.keepgoeat.data.model.response.ResponseHome
import org.keepgoeat.domain.repository.GoalRepository
import timber.log.Timber
import javax.inject.Inject

class GoalRepositoryImpl @Inject constructor(
    private val goalDataSource: GoalDataSource,
) : GoalRepository {
    override suspend fun fetchHomeEntireData(): ResponseHome.HomeData? {
        val result = goalDataSource.fetchHomeEntireData()
        return when (result) {
            is ApiResult.Success -> {
                result.data?.data
            }
            is ApiResult.NetworkError -> {
                Timber.d("Network Error")
                null
            }
            is ApiResult.GenericError -> {
                Timber.d("(${result.code}): $(result.message}")
                null
            }
        }
    }

    override suspend fun completeGoal(goalId: Int, isCompleted: Boolean): ResponseCompletedResult.Data? {
        val result = goalDataSource.completeGoal(goalId, RequestGoalCompleted(isCompleted))
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
        isMore: Boolean
    ): ResponseGoalContent.ResponseGoalContentData? {
        val result = goalDataSource.uploadGoalContent(RequestGoalContent(title, isMore))

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

    override suspend fun editGoalContent(
        id: Int,
        title: String
    ): ResponseGoalContent.ResponseGoalContentData? {
        val result = goalDataSource.editGoalContent(id, RequestGoalContentTitle(title))

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

    override suspend fun fetchGoalDetail(goalId: Int): ResponseGoalDetail.ResponseGoalDetailData? {
        val result = goalDataSource.fetchGoalDetail(goalId)

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
}
