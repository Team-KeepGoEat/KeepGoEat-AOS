package org.keepgoeat.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.keepgoeat.data.repository.AuthRepositoryImpl
import org.keepgoeat.data.repository.GoalRepositoryImpl
import org.keepgoeat.data.repository.UserRepositoryImpl
import org.keepgoeat.domain.repository.AuthRepository
import org.keepgoeat.domain.repository.GoalRepository
import org.keepgoeat.domain.repository.UserRepository
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
    fun bindAuthRepository(
        authRepositoryImpl: AuthRepositoryImpl,
    ): AuthRepository

    @Binds
    @Singleton
    fun bindUserRepository(
        userRepositoryImpl: UserRepositoryImpl,
    ): UserRepository
}
