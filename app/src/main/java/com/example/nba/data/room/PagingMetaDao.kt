package com.example.nba.data.room

import androidx.room.*
import com.example.nba.data.io.PagingMetaIO
import com.example.nba.data.room.AppRoomDatabase.Companion.ROOM_PAGING_META_TABLE

/** Interface for communication with local Room database */
@Dao
interface PagingMetaDao {

    /** Inserts all paging meta data */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(pagingMeta: List<PagingMetaIO>)

    /** returns specific paging meta data for specific player identification [id] */
    @Query("SELECT * FROM $ROOM_PAGING_META_TABLE WHERE player_id = :id")
    suspend fun getPagingMetaByPlayerId(id: Long): PagingMetaIO?

    /** deletes all paging meta data */
    @Query("DELETE FROM $ROOM_PAGING_META_TABLE")
    suspend fun removePagingMeta()

    /** returns when was the last time we donwloaded data from RestApi */
    @Query("SELECT created_at FROM $ROOM_PAGING_META_TABLE ORDER BY created_at DESC LIMIT 1")
    suspend fun getCreationTime(): Long?
}