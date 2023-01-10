package org.keepgoeat.data.datasource.remote

import org.keepgoeat.data.ApiResult
import org.keepgoeat.data.model.request.RequestGoalContent
import org.keepgoeat.data.model.request.RequestGoalContentTitle
import org.keepgoeat.data.model.response.ResponseGoalContent
import org.keepgoeat.data.model.response.ResponseGoalDetail
import org.keepgoeat.data.model.response.ResponseGoalKeep
import org.keepgoeat.data.model.response.ResponseHome
import org.keepgoeat.data.service.GoalService
import org.keepgoeat.util.safeApiCall
import javax.inject.Inject

class GoalDataSource @Inject constructor(
    private val goalService: GoalService,
) {
    suspend fun fetchHomeEntireData(): ApiResult<ResponseHome?> =
        safeApiCall { goalService.fetchHomeEntireData() }

    suspend fun uploadGoalContent(requestGoalContent: RequestGoalContent): ApiResult<ResponseGoalContent?> =
        safeApiCall { goalService.uploadGoalContent(requestGoalContent) }

    suspend fun editGoalContent(id: Int, title: RequestGoalContentTitle): ApiResult<ResponseGoalContent?> =
        safeApiCall { goalService.editGoalContent(id, title) }

    // TODO goalId -> id 로 파라미터명 변경
    suspend fun fetchGoalDetail(goalId: Int): ApiResult<ResponseGoalDetail?> =
        safeApiCall { goalService.fetchGoalDetail(goalId) }

    suspend fun keepGoal(id: Int): ApiResult<ResponseGoalKeep?> =
        safeApiCall { goalService.keepGoal(id) }
}
