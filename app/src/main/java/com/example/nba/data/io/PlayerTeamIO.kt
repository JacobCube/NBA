package com.example.nba.data.io

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.nba.data.room.AppRoomDatabase
import com.google.gson.annotations.SerializedName
import java.io.Serializable

/** Information about specific player team */
@Entity(tableName = AppRoomDatabase.ROOM_TEAM_TABLE)
data class PlayerTeamIO(

    /** Player unique identification */
    @PrimaryKey
    val id: Long? = null,

    /** team's shortened name */
    val abbreviation: String? = null,

    /** team's home city */
    val city: String? = null,

    /** team's conference */
    val conference: String? = null,

    /** team's division in which it plays */
    val division: String? = null,

    /** team's full name */
    @SerializedName("full_name")
    val fullName: String? = null,

    /** team's short name */
    val name: String? = null,

): Serializable