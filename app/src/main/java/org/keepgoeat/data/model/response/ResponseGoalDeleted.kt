package org.keepgoeat.data.model.response

import kotlinx.serialization.Serializable

@Serializable
data class ResponseGoalDeleted(
    val data: ResponseGoalDeletedData,
    val message: String,
    val status: Int,
    val success: Boolean
) {
    @Serializable
    data class ResponseGoalDeletedData(
        val goalId: Int
    )
}
