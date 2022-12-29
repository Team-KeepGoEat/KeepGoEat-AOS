package org.keepgoeat.data.repository

import org.keepgoeat.data.datasource.remote.DummyDataSource
import org.keepgoeat.data.model.request.RequestData
import org.keepgoeat.domain.repository.DummyRepository
import javax.inject.Inject

class DummyRepositoryImpl @Inject constructor(
    private val dummyDataSource: DummyDataSource,
) : DummyRepository {
    override suspend fun uploadDummy(name: String, email: String) {
        dummyDataSource.uploadDummy(RequestData(name, email))
    }
}
