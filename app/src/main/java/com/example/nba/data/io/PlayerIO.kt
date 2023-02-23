package com.example.nba.data.io

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/** Information about specific player */
data class PlayerIO(

    /** Player unique identification */
    val id: Long? = null,

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
    //TODO probably ENUM, better leave as string for now
    val position: String? = null,

    /** in which team does this player play in */
    val team: PlayerTeamIO? = null,

    /** player's weight in pounds */
    @SerializedName("weight_pounds")
    val weightPounds: Int? = null,

    ): Serializable