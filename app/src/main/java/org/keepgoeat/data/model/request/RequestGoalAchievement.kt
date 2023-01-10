package org.keepgoeat.data.model.request

import kotlinx.serialization.Serializable

@Serializable
data class RequestGoalAchievement(
    val isAchieved: Boolean
)
