package org.keepgoeat.data.repository

import org.keepgoeat.data.ApiResult
import org.keepgoeat.data.datasource.remote.DummyDataSource
import org.keepgoeat.data.model.request.RequestData
import org.keepgoeat.data.model.response.ResponseListResult
import org.keepgoeat.domain.repository.DummyRepository
import timber.log.Timber
import javax.inject.Inject

class DummyRepositoryImpl @Inject constructor(
    private val dummyDataSource: DummyDataSource,
) : DummyRepository {
    override suspend fun uploadDummy(name: String, email: String): ResponseListResult? {
        val result = dummyDataSource.uploadDummy(RequestData(name, email))

        return when (result) {
            is ApiResult.Success -> { result.data }
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
