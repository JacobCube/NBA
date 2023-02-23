package com.example.nba.data.io

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/** Information about specific player team */
data class PlayerTeamIO(

    /** Player unique identification */
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