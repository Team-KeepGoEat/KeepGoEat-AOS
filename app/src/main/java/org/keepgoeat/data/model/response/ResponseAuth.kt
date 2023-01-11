package org.keepgoeat.data.model.response

import kotlinx.serialization.Serializable

@Serializable
data class ResponseAuth(
    val data: ResponseAuthData?,
    val message: String,
    val status: Int,
    val success: Boolean,
) {
    @Serializable
    data class ResponseAuthData(
        val type: String,
        val email: String,
        val accessToken: String,
        val refreshToken: String,
    )
}
