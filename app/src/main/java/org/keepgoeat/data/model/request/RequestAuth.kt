package org.keepgoeat.data.model.request

import kotlinx.serialization.Serializable

@Serializable
data class RequestAuth(
    val platformAccessToken: String,
    val platform: String = "KAKAO",
)
