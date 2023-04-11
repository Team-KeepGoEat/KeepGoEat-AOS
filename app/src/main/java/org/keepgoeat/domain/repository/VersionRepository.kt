package org.keepgoeat.domain.repository

import org.keepgoeat.data.model.response.ResponseVersion

interface VersionRepository {
    suspend fun fetchVersion(clientType: String): Result<ResponseVersion.VersionData>
}
