package org.keepgoeat.data.model.response

import kotlinx.serialization.Serializable

@Serializable
data class ResponseGoalKeep(
    val data: ResponseGoalKeepData,
    val message: String,
    val status: Int,
    val success: Boolean
) {
    @Serializable
    data class ResponseGoalKeepData(
        val goalId: Int
    )
}
