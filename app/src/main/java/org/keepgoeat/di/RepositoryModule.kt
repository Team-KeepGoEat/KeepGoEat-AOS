package org.keepgoeat.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.keepgoeat.data.repository.DummyRepositoryImpl
import org.keepgoeat.domain.repository.DummyRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Binds
    @Singleton
    fun bindDummyRepository(
        dummyRepositoryImpl: DummyRepositoryImpl,
    ): DummyRepository
}
