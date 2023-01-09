package org.keepgoeat.data.datasource.remote

import org.keepgoeat.data.ApiResult
import org.keepgoeat.data.model.response.ResponseMy
import org.keepgoeat.data.service.MyService
import org.keepgoeat.util.safeApiCall
import javax.inject.Inject

class MyDataSource @Inject constructor(
    private val myService: MyService
) {
    suspend fun fetchMyData(sortType: String): ApiResult<ResponseMy?> =
        safeApiCall { myService.fetchMyData(sortType) }
}
