package org.keepgoeat.data.model.request

import kotlinx.serialization.Serializable

@Serializable
data class RequestGoalContentTitle(
    val goalContent: String,
)
