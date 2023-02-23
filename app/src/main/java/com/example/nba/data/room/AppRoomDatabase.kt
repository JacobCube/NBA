package com.example.nba.data.room

import androidx.room.*
import com.example.nba.data.io.PagingMetaIO
import com.example.nba.data.io.PlayerIO
import com.example.nba.data.io.PlayerTeamIO

@Database(
    entities = [PlayerIO::class, PlayerTeamIO::class, PagingMetaIO::class],
    version = 2,
    exportSchema = false
)
@TypeConverters(AppDatabaseConverter::class)
abstract class AppRoomDatabase: RoomDatabase() {

    abstract fun playerDbDao(): PlayerDao
    abstract fun pagingMetaDbDao(): PagingMetaDao

    companion object {

        /** Identification of the main database */
        const val ROOM_DATABASE_NAME = "NBA_ROOM"

        /** Identification of table for [PlayerIO] */
        const val ROOM_PLAYER_TABLE = "player_table"

        /** Identification of table for [PlayerTeamIO] */
        const val ROOM_TEAM_TABLE = "team_player_table"

        /** Identification of table for [PagingMetaIO] */
        const val ROOM_PAGING_META_TABLE = "paging_meta_table"
    }
}