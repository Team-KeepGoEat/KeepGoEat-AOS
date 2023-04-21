package org.keepgoeat.data.service

import org.keepgoeat.data.model.response.ResponseVersion
import retrofit2.http.GET
import retrofit2.http.Query

interface VersionService {
    /** 최신 강제 업데이트 버전을 불러오는 함수 */
    @GET("ver")
    suspend fun getForcedUpdateVersion(
        @Query("client") value: String,
    ): ResponseVersion
}
