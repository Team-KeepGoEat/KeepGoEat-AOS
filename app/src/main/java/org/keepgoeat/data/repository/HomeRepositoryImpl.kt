package org.keepgoeat.data.repository

import org.keepgoeat.data.ApiResult
import org.keepgoeat.data.datasource.remote.HomeDataSource
import org.keepgoeat.data.model.response.ResponseHome
import org.keepgoeat.domain.repository.HomeRepository
import timber.log.Timber
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val homeDataSource: HomeDataSource,
) : HomeRepository {
    override suspend fun fetchHome(): ResponseHome.HomeData? {
        val result = homeDataSource.fetchHomeEntire()
        return when (result) {
            is ApiResult.Success -> {
                result.data?.homeData
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
