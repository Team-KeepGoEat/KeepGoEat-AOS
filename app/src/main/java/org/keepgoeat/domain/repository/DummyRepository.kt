package org.keepgoeat.domain.repository

import org.keepgoeat.data.model.response.ResponseListResult

interface DummyRepository {
    suspend fun uploadDummy(name: String, email: String): ResponseListResult?
}
