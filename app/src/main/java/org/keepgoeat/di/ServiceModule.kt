package org.keepgoeat.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.keepgoeat.data.service.DummyService
import org.keepgoeat.data.service.GoalService
import org.keepgoeat.data.service.MyService
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {
    @Singleton
    @Provides
    fun provideDummyService(retrofit: Retrofit): DummyService =
        retrofit.create(DummyService::class.java)

    @Singleton
    @Provides
    fun provideGoalService(retrofit: Retrofit): GoalService =
        retrofit.create(GoalService::class.java)

    @Singleton
    @Provides
    fun provideMyService(retrofit: Retrofit): MyService =
        retrofit.create(MyService::class.java)
}
