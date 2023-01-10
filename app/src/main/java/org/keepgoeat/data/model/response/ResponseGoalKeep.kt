package org.keepgoeat.data.model.response

import kotlinx.serialization.Serializable

@Serializable
data class ResponseGoalKeep(
    val data: Data?,
    val message: String,
    val status: Int,
    val success: Boolean
) {
    @Serializable
    data class Data(
        val goalId: Int
    )
}
