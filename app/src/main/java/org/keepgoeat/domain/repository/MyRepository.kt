package org.keepgoeat.domain.repository

import org.keepgoeat.data.model.response.ResponseMy

interface MyRepository {
    suspend fun fetchMyData(sortType: String): ResponseMy.MyData?
}
