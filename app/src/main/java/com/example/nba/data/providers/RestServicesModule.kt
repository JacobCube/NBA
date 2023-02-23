package com.example.nba.data.providers

import com.example.nba.data.PlayersRestApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import retrofit2.Retrofit

@Module
@InstallIn(ActivityRetainedComponent::class)
object RestServicesModule {

    /** Interface for working with players */
    @ActivityRetainedScoped
    @Provides
    fun providePlayerRestApi(retrofit: Retrofit): PlayersRestApi = retrofit.create(PlayersRestApi::class.java)
}