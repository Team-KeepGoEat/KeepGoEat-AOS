package org.keepgoeat.data.model.request

import kotlinx.serialization.Serializable

@Serializable
data class RequestData(
    val name: String,
    val email: String,
)
