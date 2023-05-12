package org.keepgoeat.data.model.response

import kotlinx.serialization.Serializable
import org.keepgoeat.domain.model.UserInfo

@Serializable
data class ResponseUserInfo(
    val data: ResponseUserData?,
    val message: String,
    val status: Int,
    val success: Boolean,
) {
    @Serializable
    data class ResponseUserData(
        val name: String,
        val email: String,
        val keptGoalsCount: Int,
    ) {
        fun toUserInfo() = UserInfo(name, email, keptGoalsCount)
    }
}
