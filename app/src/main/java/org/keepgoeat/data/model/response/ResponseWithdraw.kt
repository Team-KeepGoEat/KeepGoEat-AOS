package org.keepgoeat.data.model.response

import kotlinx.serialization.Serializable

@Serializable
data class ResponseWithdraw(
    val message: String,
    val status: Int,
    val success: Boolean,
)
