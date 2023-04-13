package org.keepgoeat.data.repository

import org.keepgoeat.data.datasource.remote.VersionDataSource
import org.keepgoeat.data.model.response.ResponseVersion
import org.keepgoeat.domain.repository.VersionRepository
import javax.inject.Inject

class VersionRepositoryImpl @Inject constructor(
    private val versionDataSource: VersionDataSource
) : VersionRepository {
    override suspend fun getForcedUpdateVersion(clientType: String): Result<ResponseVersion.VersionData> =
        runCatching {
            versionDataSource.getForcedUpdateVersion(clientType).data
        }
}
