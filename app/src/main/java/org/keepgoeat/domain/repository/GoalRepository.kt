package org.keepgoeat.domain.repository

import org.keepgoeat.data.model.response.ResponseHome

interface GoalRepository {
    suspend fun fetchHome(): ResponseHome.HomeData?
}
