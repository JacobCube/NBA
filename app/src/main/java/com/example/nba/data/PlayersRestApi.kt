package com.example.nba.data

import com.example.nba.data.io.PlayerIO
import com.example.nba.data.io.PlayerTeamIO
import com.example.nba.data.io.PlayersListResponseIO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PlayersRestApi {

    /**
     * Request for a specific page of players
     * @param pageIndex index of page that should be retrieved
     * @param perPage how many items should there be per page
     */
    @GET("v1/players")
    suspend fun getPlayersListPage(
        @Query("page") pageIndex: Int,
        @Query("per_page") perPage: Int,
        @Query("search") search: String? = null
    ): Response<PlayersListResponseIO>

    /**
     * Request for a specific player by their identification
     * @param playerId identifier of a player we want to request
     */
    @GET("v1/players/{playerId}")
    suspend fun getPlayerById(@Path("playerId") playerId: String): Response<PlayerIO>

    /**
     * Request for a specific player by their identification
     * @param teamId identifier of a team we want to request
     */
    @GET("v1/teams/{teamId}")
    suspend fun getTeamById(@Path("teamId") teamId: String): Response<PlayerTeamIO>
}