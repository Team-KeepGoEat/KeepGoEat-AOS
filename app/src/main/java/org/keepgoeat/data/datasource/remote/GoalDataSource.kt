package org.keepgoeat.data.datasource.remote

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import org.keepgoeat.data.ApiResult
import org.keepgoeat.data.model.request.RequestGoalAchievement
import org.keepgoeat.data.model.request.RequestGoalContent
import org.keepgoeat.data.model.request.RequestGoalContentTitle
import org.keepgoeat.data.model.response.*
import org.keepgoeat.data.safeFlow
import org.keepgoeat.data.service.GoalService
import org.keepgoeat.di.IoDispatcher
import org.keepgoeat.util.safeApiCall
import javax.inject.Inject

class GoalDataSource @Inject constructor(
    private val goalService: GoalService,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {
    suspend fun fetchHomeEntireData(): Flow<ApiResult<ResponseHome.HomeData?>> =
        safeFlow { goalService.fetchHomeEntireData().body()?.data }.flowOn(ioDispatcher)

    suspend fun uploadGoalContent(requestGoalContent: RequestGoalContent): ApiResult<ResponseGoalContent?> =
        safeApiCall { goalService.uploadGoalContent(requestGoalContent) }

    suspend fun editGoalContent(
        id: Int,
        title: RequestGoalContentTitle
    ): ApiResult<ResponseGoalContent?> =
        safeApiCall { goalService.editGoalContent(id, title) }

    suspend fun fetchGoalDetail(id: Int): ApiResult<ResponseGoalDetail?> =
        safeApiCall { goalService.fetchGoalDetail(id) }

    suspend fun keepGoal(id: Int): ApiResult<ResponseGoalKeep?> =
        safeApiCall { goalService.keepGoal(id) }

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
