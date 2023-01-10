package org.keepgoeat.data.model.response

import kotlinx.serialization.Serializable

@Serializable
data class ResponseCompletedResult(
    val data: Data?,
    val message: String,
    val status: Int,
    val success: Boolean
) {
    @Serializable
    data class Data(
        val goalId: Int,
        val thisMonthCount: Int,
        val updatedIsAchieved: Boolean
    )
}