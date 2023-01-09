package org.keepgoeat.data.datasource.remote

import org.keepgoeat.data.ApiResult
import org.keepgoeat.data.model.response.ResponseHome
import org.keepgoeat.data.service.GoalService
import org.keepgoeat.util.safeApiCall
import javax.inject.Inject

class GoalDataSource @Inject constructor(
    private val goalService: GoalService
) {
    suspend fun fetchHomeEntireData(): ApiResult<ResponseHome?> =
        safeApiCall { goalService.fetchHomeEntireData() }
}
