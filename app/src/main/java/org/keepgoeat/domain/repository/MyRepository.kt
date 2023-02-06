package org.keepgoeat.domain.repository

import org.keepgoeat.domain.model.MyGoal

interface MyRepository {
    suspend fun fetchMyData(sortType: String): Result<List<MyGoal>>
}
