package org.keepgoeat.data.datasource.remote

import org.keepgoeat.data.model.request.RequestData
import org.keepgoeat.data.model.response.ResponseResult
import org.keepgoeat.data.service.DummyService
import javax.inject.Inject

class DummyDataSource @Inject constructor(
    private val dummyService: DummyService,
) {
    suspend fun uploadDummy(signInRequest: RequestData): ResponseResult {
        //        dummyService.uploadDummy(signInRequest) // TODO api 요청하기
        return ResponseResult(
            1,
            "success",
            ResponseResult.ResponseData(1, "keepgoeat", "keepgoeat@gmamil.com")
        )
    }
}
