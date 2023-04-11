package org.keepgoeat.data.datasource.remote

import org.keepgoeat.data.model.response.ResponseVersion
import org.keepgoeat.data.service.VersionService
import javax.inject.Inject

class VersionDataSource @Inject constructor(
    private val versionService: VersionService,
) {
    suspend fun fetchVersion(clientType: String): ResponseVersion =
        versionService.fetchVersion(clientType)
}
