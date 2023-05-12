package org.keepgoeat.data.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseGoalContent(
    val status: Int,
    val success: Boolean,
    val message: String,
    val data: ResponseGoalContentData,
) {
    @Serializable
    data class ResponseGoalContentData(
        @SerialName("goalId")
        val id: Int,
    )
}
