package org.keepgoeat.data.model.response

import kotlinx.serialization.Serializable

@Serializable
data class ResponseGoalAchievement(
    val data: ResponseGoalAchievementData,
    val message: String,
    val status: Int,
    val success: Boolean
) {
    @Serializable
    data class ResponseGoalAchievementData(
        val goalId: Int,
        val thisMonthCount: Int,
        val updatedIsAchieved: Boolean
    )
}
