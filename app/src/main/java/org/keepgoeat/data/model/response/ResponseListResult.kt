package org.keepgoeat.data.model.response

import kotlinx.serialization.Serializable
import org.keepgoeat.domain.model.DummyData

@Serializable
data class ResponseListResult(
    val status: Int,
    val message: String,
    val data: List<ResponseData>,
) {
    @Serializable
    data class ResponseData(
        // TODO ResponseUser가 중복으로 사용된다면 파일 분리하기
        val id: Int,
        val name: String,
        val email: String,
    )

    fun toDummyDataList(): List<DummyData> = data.map {
        DummyData(
            id = it.id.toString(),
            email = it.email,
            name = it.name
        )
    }
}
