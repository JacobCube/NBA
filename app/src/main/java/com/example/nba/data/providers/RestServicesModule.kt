package com.example.nba.data.providers

import com.example.nba.data.PlayersRestApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import retrofit2.Retrofit

/** Main module provider for accessing interfaces for RestApi */
@Module
@InstallIn(ActivityRetainedComponent::class)
object RestServicesModule {

    /** Interface for access of RestAPI for players */
    @ActivityRetainedScoped
    @Provides
    fun providePlayerRestApi(retrofit: Retrofit): PlayersRestApi = retrofit.create(PlayersRestApi::class.java)
}