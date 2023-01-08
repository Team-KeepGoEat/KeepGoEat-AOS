package org.keepgoeat.domain.repository

import org.keepgoeat.data.model.response.ResponseHome

interface HomeRepository {
    suspend fun fetchHome(): ResponseHome.HomeData?
}