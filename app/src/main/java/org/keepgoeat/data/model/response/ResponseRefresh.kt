package org.keepgoeat.data.model.response

import kotlinx.serialization.Serializable

@Serializable
data class ResponseRefresh(
    val status: Int,
    val success: Boolean,
    val message: String,
    val data: ResponseToken,
) {
    @Serializable
    data class ResponseToken(
        val accessToken: String,
        val refreshToken: String
    )
}
