package org.keepgoeat.data.model.request

import kotlinx.serialization.Serializable

@Serializable
data class RequestGoalContent(
    val food: String?,
    val criterion: String,
    val isMore: Boolean,
)
