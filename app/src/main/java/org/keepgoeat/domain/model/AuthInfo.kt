package org.keepgoeat.domain.model

import org.keepgoeat.domain.type.SignType

data class AuthInfo(
    val signType: SignType,
    val email: String,
    val accessToken: String,
    val refreshToken: String,
)
