package org.keepgoeat.data.model.request

import kotlinx.serialization.Serializable

@Serializable
data class RequestEditedGoalContent(
    val food: String,
    val criterion: String
)
