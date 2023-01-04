package org.keepgoeat.data.datasource.remote

import org.keepgoeat.data.ApiResult
import org.keepgoeat.data.model.request.RequestData
import org.keepgoeat.data.model.response.ResponseListResult
import org.keepgoeat.data.service.DummyService
import org.keepgoeat.util.safeApiCall
import javax.inject.Inject

class DummyDataSource @Inject constructor(
    private val dummyService: DummyService,
) {
    suspend fun uploadDummy(signInRequest: RequestData): ApiResult<ResponseListResult?> =
        safeApiCall { dummyService.uploadDummy(signInRequest) }
}
