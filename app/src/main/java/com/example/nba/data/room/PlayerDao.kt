package com.example.nba.data.room

import androidx.paging.PagingSource
import androidx.room.*
import com.example.nba.data.io.PlayerIO
import com.example.nba.data.io.PlayerTeamIO

/** Interface for communication with local Room database */
@Dao
interface PlayerDao {

    /** Paginated data for RemoteMediator */
    @Query("SELECT * FROM ${AppRoomDatabase.ROOM_PLAYER_TABLE} ORDER BY page")
    fun getAllPlayers(): PagingSource<Int, PlayerIO>

    /** Returns a single player based on their identification [playerId] */
    @Query("SELECT * FROM ${AppRoomDatabase.ROOM_PLAYER_TABLE} WHERE id == :playerId LIMIT 1")
    suspend fun getPlayerById(playerId: String): PlayerIO?

    /** Returns a single team based on their identification [teamId] */
    @Query("SELECT * FROM ${AppRoomDatabase.ROOM_TEAM_TABLE} WHERE id == :teamId LIMIT 1")
    suspend fun getTeamById(teamId: String): PlayerTeamIO?

    /** Inserts or updates a new player object [player] into the database */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlayer(player: PlayerIO)

    /** Inserts or updates a new team object [team] into the database */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTeam(team: PlayerTeamIO)

    /** Inserts or updates a set of player objects [players] */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllPlayers(players: List<PlayerIO>)

    /** Removes all players from the database */
    @Query("DELETE FROM ${AppRoomDatabase.ROOM_PLAYER_TABLE}")
    suspend fun removeAllPlayers()
}