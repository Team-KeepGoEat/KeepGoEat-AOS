package org.keepgoeat.data.datasource.remote

import org.keepgoeat.data.model.response.ResponseMy
import org.keepgoeat.data.service.MyService
import javax.inject.Inject

class MyDataSource @Inject constructor(
    private val myService: MyService
) {
    suspend fun fetchMyData(sortType: String): ResponseMy =
        myService.fetchMyData(sortType)
}
