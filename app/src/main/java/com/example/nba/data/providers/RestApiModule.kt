package com.example.nba.data.providers

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/** Module provider for working with Retrofit */
@Module
@InstallIn(ActivityRetainedComponent::class)
object RestApiModule {

    /** Base URL off of which everything gets downloaded from */
    @Provides
    fun provideBaseUrl() = "https://www.balldontlie.io/api/"

    /** Retrofit instance for providing API services and converting JSON to Kotlin */
    @ActivityRetainedScoped
    @Provides
    fun provideRetrofit(httpClient: OkHttpClient, baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient)
            .build()
    }

    /** Retrofit instance for connecting and requesting */
    @ActivityRetainedScoped
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().also { httpClientBuilder ->
            httpClientBuilder.retryOnConnectionFailure(true)
            httpClientBuilder.readTimeout(60, TimeUnit.SECONDS)
            httpClientBuilder.connectTimeout(60, TimeUnit.SECONDS)
        }.build()
    }
}