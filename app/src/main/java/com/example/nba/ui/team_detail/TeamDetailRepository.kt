package com.example.nba.ui.team_detail

import com.example.nba.data.PlayersRestApi
import com.example.nba.data.io.PlayerTeamIO
import com.example.nba.data.room.PlayerDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Repository for requesting the NBA API
 */
class TeamDetailRepository @Inject constructor(
    private val restApi: PlayersRestApi,
    private val playerDao: PlayerDao
) {

    /**
     * Request for a specific NBA team by their identification
     */
    suspend fun getTeamById(
        teamId: String,
    ): PlayerTeamIO? {
        return withContext(Dispatchers.IO) {
            playerDao.getTeamById(teamId)
                ?: restApi.getTeamById(teamId).body()?.also {
                    playerDao.insertTeam(it)
                }
        }
    }
}