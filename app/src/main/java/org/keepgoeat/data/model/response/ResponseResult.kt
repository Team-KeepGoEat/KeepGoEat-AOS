package org.keepgoeat.data.model.response

import kotlinx.serialization.Serializable
import org.keepgoeat.domain.model.DummyData

@Serializable
data class ResponseResult(
    val status: Int,
    val message: String,
    val data: ResponseData,
) {
    @Serializable
    data class ResponseData( // TODO ResponseUser가 중복으로 사용된다면 파일 분리하기
        val id: Int,
        val name: String,
        val email: String,
    ) {
        fun toDummyData() = DummyData(
            id = id.toString(),
            email = email,
            name = name
        )
    }
}
