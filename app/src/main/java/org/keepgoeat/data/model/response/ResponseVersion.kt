package org.keepgoeat.data.model.response

import kotlinx.serialization.Serializable

@Serializable
data class ResponseVersion(
    val status: Int,
    val success: Boolean,
    val message: String,
    val data: VersionData
) {
    @Serializable
    data class VersionData(
        val version: String
    )
}