package org.keepgoeat.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.keepgoeat.data.repository.AuthRepositoryImpl
import org.keepgoeat.data.repository.GoalRepositoryImpl
import org.keepgoeat.data.repository.MyRepositoryImpl
import org.keepgoeat.domain.repository.AuthRepository
import org.keepgoeat.domain.repository.GoalRepository
import org.keepgoeat.domain.repository.MyRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Binds
    @Singleton
    fun bindGoalRepository(
        goalRepositoryImpl: GoalRepositoryImpl,
    ): GoalRepository

    @Binds
    @Singleton
    fun bindMyRepository(
        myRepositoryImRepositoryImpl: MyRepositoryImpl,
    ): MyRepository

    @Binds
    @Singleton
    fun bindAuthRepository(
        authRepositoryImpl: AuthRepositoryImpl,
    ): AuthRepository
}
