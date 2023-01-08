package org.keepgoeat.di

import android.content.Context
import com.kakao.sdk.user.UserApiClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.scopes.ActivityScoped
import org.keepgoeat.data.service.SignService

@Module
@InstallIn(ActivityComponent::class)
object SignModule {
    @Provides
    @ActivityScoped
    fun provideUserApiClient(): UserApiClient = UserApiClient.instance

    @Provides
    fun provideSignService(
        @ActivityContext context: Context,
        client: UserApiClient
    ) = SignService(context, client)
}
