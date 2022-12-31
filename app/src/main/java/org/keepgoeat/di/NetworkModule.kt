package org.keepgoeat.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.keepgoeat.BuildConfig.DEBUG
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @OptIn(ExperimentalSerializationApi::class)
    @Provides
    @Singleton
    fun provideJson(): Json = Json {
        isLenient = true
        prettyPrint = true
        explicitNulls = false
        ignoreUnknownKeys = true
    }

    @ExperimentalSerializationApi
    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient, json: Json): Retrofit = Retrofit.Builder()
        .baseUrl("https://reqres.in/") // TODO BaseUrl 변경 및 BuildConfig 변수 사용
        .client(client)
        .addConverterFactory(json.asConverterFactory(requireNotNull("application/json".toMediaTypeOrNull())))
        .build()

    @Provides
    @Singleton
    fun provideOkHttpClientBuilder(): OkHttpClient =
        OkHttpClient.Builder().apply {
            connectTimeout(10, TimeUnit.SECONDS)
            writeTimeout(10, TimeUnit.SECONDS)
            readTimeout(10, TimeUnit.SECONDS)
            if (DEBUG) addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            )
            // TODO AuthInterceptor 추가
        }.build()
}
