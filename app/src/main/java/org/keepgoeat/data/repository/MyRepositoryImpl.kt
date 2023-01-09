package org.keepgoeat.data.repository

import org.keepgoeat.data.ApiResult
import org.keepgoeat.data.datasource.remote.MyDataSource
import org.keepgoeat.data.model.response.ResponseMy
import org.keepgoeat.domain.repository.MyRepository
import timber.log.Timber
import javax.inject.Inject

class MyRepositoryImpl @Inject constructor(
    private val myDataSource: MyDataSource
) : MyRepository {
    override suspend fun fetchMyData(sortType: String): ResponseMy.MyData? {
        val result = myDataSource.fetchMyData(sortType)
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
}
