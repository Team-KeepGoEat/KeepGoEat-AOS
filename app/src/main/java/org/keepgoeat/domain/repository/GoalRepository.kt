package org.keepgoeat.domain.repository

import org.keepgoeat.data.model.response.ResponseGoalContent
import org.keepgoeat.data.model.response.ResponseGoalDetail
import org.keepgoeat.data.model.response.ResponseHome

interface GoalRepository {
    suspend fun fetchHomeEntireData(): ResponseHome.HomeData?
    suspend fun uploadGoalContent(title: String, isMore: Boolean): ResponseGoalContent.ResponseGoalContentData?
    suspend fun editGoalContent(id: Int, title: String): ResponseGoalContent.ResponseGoalContentData?
    suspend fun fetchGoalDetail(goalId: Int): ResponseGoalDetail.ResponseGoalDetailData?
}
