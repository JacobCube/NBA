package com.example.nba.data.io

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.nba.data.room.AppRoomDatabase
import com.google.gson.annotations.SerializedName
import java.io.Serializable

/** Meta information of paging response */
@Entity(tableName = AppRoomDatabase.ROOM_PAGING_META_TABLE)
data class PagingMetaIO(

    /** Local room database identifier */
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo("player_id")
    val playerId: Long? = null,

    /** How many pages are there in this database in total */
    @SerializedName("total_pages")
    val totalPages: Int? = null,

    /** Index of this page */
    @SerializedName("current_page")
    val currentPage: Int? = null,

    /** A flag whether there is another page after this one or not */
    @SerializedName("next_page")
    val nextPage: Int? = null,

    /** How many items are there received per page */
    @SerializedName("per_page")
    val perPage: Int? = null,

    /** How many items are there in this database in total */
    @SerializedName("total_count")
    val totalCount: Int? = null,

    /** At what time was this object created in milliseconds */
    @ColumnInfo(name = "created_at")
    val createdAt: Long = System.currentTimeMillis(),

    /** Index of previous page, mainly for local Room database */
    val previousPage: Int? = null

): Serializable