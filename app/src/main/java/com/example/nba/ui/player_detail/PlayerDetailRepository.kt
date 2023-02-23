package com.example.nba.ui.player_detail

import com.example.nba.data.PlayersRestApi
import com.example.nba.data.io.PlayerIO
import com.example.nba.data.room.PlayerDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Repository for requesting the NBA API
 */
class PlayerDetailRepository @Inject constructor(
     private val restApi: PlayersRestApi,
     private val playerDao: PlayerDao
) {

    /**
     * Request for a specific NBA player by their identification
     */
    suspend fun getPlayerById(
        playerId: String,
    ): PlayerIO? {
        return withContext(Dispatchers.IO) {
            playerDao.getPlayerById(playerId)
                ?: restApi.getPlayerById(playerId).body()?.also {
                    playerDao.insertPlayer(it)
                }
        }
    }
}