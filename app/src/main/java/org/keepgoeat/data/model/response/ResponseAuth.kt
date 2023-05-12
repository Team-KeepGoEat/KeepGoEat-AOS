package org.keepgoeat.data.model.response

import kotlinx.serialization.Serializable
import org.keepgoeat.domain.model.AuthInfo
import org.keepgoeat.domain.type.SignType.SIGN_IN
import org.keepgoeat.domain.type.SignType.SIGN_UP

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
    ) {
        fun toAuthInfo() = AuthInfo(
            if (type == SIGN_UP.typeStr) SIGN_UP else SIGN_IN,
            email,
            accessToken,
            refreshToken
        )
    }
}
