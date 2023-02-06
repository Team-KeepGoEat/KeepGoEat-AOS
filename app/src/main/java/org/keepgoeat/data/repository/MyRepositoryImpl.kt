package org.keepgoeat.data.repository

import org.keepgoeat.data.datasource.remote.MyDataSource
import org.keepgoeat.domain.model.MyGoal
import org.keepgoeat.domain.repository.MyRepository
import javax.inject.Inject

class MyRepositoryImpl @Inject constructor(
    private val myDataSource: MyDataSource,
) : MyRepository {
    override suspend fun fetchMyData(sortType: String): Result<List<MyGoal>> =
        runCatching {
            myDataSource.fetchMyData(sortType).data.toMyGoal()
        }
}
