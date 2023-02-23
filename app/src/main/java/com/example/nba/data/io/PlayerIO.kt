package com.example.nba.data.io

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.nba.data.room.AppRoomDatabase
import com.google.gson.annotations.SerializedName
import java.io.Serializable

/** Information about specific player */
@Entity(tableName = AppRoomDatabase.ROOM_PLAYER_TABLE)
data class PlayerIO(

    /** Player unique identification */
    @PrimaryKey
    val id: Long? = null,

    /** What page is this entity related to */
    var page: Int? = null,

    /** first name of the player */
    @SerializedName("first_name")
    val firstName: String? = null,

    /** height of the player in feet */
    @SerializedName("height_feet")
    val heightFeet: Int? = null,

    /** height of the player in inches */
    @SerializedName("height_inches")
    val heightInches: Int? = null,

    /** last name of the player */
    @SerializedName("last_name")
    val lastName: String? = null,

    /** position in field */
    val position: String? = null,

    /** in which team does this player play in */
    val team: PlayerTeamIO? = null,

    /** player's weight in pounds */
    @SerializedName("weight_pounds")
    val weightPounds: Int? = null

): Serializable