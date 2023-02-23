package com.example.nba.data.providers

import android.content.Context
import androidx.room.Room
import com.example.nba.data.room.AppDatabaseConverter
import com.example.nba.data.room.AppRoomDatabase
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityRetainedScoped

/** Main module provider for accessing interfaces for local Room database */
@Module
@InstallIn(ActivityRetainedComponent::class)
object DatabaseServicesModule {

    /** Interface for accessing players in local Room database */
    @Provides
    fun providePlayerDao(appDatabase: AppRoomDatabase) = appDatabase.playerDbDao()

    /** Interface for accessing paging meta data in local Room database */
    @Provides
    fun providePagingMetaDao(appDatabase: AppRoomDatabase) = appDatabase.pagingMetaDbDao()

    /** Local main Room database */
    @ActivityRetainedScoped
    @Provides
    fun provideAppDatabase(
        @ApplicationContext appContext: Context,
        gson: Gson
    ) = Room.databaseBuilder(
        appContext,
        AppRoomDatabase::class.java,
        AppRoomDatabase.ROOM_DATABASE_NAME
    ).fallbackToDestructiveMigration()
        .addTypeConverter(AppDatabaseConverter(gson))
        .build()

    /** Singleton instance of a Gson converter, since it's a costly initiated, it's faster this way */
    @ActivityRetainedScoped
    @Provides
    fun provideGsonInstance() = Gson()
}