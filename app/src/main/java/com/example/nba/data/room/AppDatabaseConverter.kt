package com.example.nba.data.room

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.example.nba.data.io.PagingMetaIO
import com.example.nba.data.io.PlayerIO
import com.example.nba.data.io.PlayerTeamIO
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import javax.inject.Inject

/** Factory converter for Room database */
@ProvidedTypeConverter
class AppDatabaseConverter @Inject constructor(private val gson: Gson) {

    /** Converts [PlayerIO] object to string */
    @TypeConverter
    fun fromPlayerIO(value: PlayerIO): String {
        return gson.toJson(value, object: TypeToken<PlayerIO>() {}.type)
    }

    /** Converts [PlayerTeamIO] object to string */
    @TypeConverter
    fun fromTeamIO(value: PlayerTeamIO): String {
        return gson.toJson(value, object: TypeToken<PlayerTeamIO>() {}.type)
    }

    /** Converts string to [PlayerIO] object */
    @TypeConverter
    fun toPlayerIO(value: String): PlayerIO {
        return gson.fromJson(value, object: TypeToken<PlayerIO>() {}.type)
    }

    /** Converts string to [PlayerIO] PlayerTeamIO */
    @TypeConverter
    fun toTeamIO(value: String): PlayerTeamIO {
        return gson.fromJson(value, object: TypeToken<PlayerTeamIO>() {}.type)
    }

    /** Converts [PlayerIO] object to string */
    @TypeConverter
    fun toPagingMetaIO(value: PagingMetaIO): String {
        return gson.toJson(value, object: TypeToken<PagingMetaIO>() {}.type)
    }

    /** Converts string to [PlayerIO] PlayerTeamIO */
    @TypeConverter
    fun toPagingMetaIO(value: String): PagingMetaIO {
        return gson.fromJson(value, object: TypeToken<PagingMetaIO>() {}.type)
    }
}