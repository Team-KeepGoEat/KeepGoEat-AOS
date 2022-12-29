package org.keepgoeat.domain.repository

interface DummyRepository {
    suspend fun uploadDummy(name: String, email: String)
}
