package org.keepgoeat.data.datasource.remote

import org.keepgoeat.data.ApiResult
import org.keepgoeat.data.model.response.ResponseHome
import org.keepgoeat.data.service.HomeService
import org.keepgoeat.util.safeApiCall
import javax.inject.Inject

class HomeDataSource @Inject constructor(
    private val homeService : HomeService
){
    suspend fun fetchHomeEntire(): ApiResult<ResponseHome?> =
        safeApiCall { homeService.fetchHomeEntire() }
}
