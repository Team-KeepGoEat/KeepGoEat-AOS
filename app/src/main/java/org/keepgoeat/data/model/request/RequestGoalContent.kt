package org.keepgoeat.data.model.request

import kotlinx.serialization.Serializable

@Serializable
data class RequestGoalContent(
    val goalContent: String,
    val isMore: Boolean,
)
