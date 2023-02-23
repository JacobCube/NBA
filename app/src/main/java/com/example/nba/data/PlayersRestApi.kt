package com.example.nba.data

import com.example.nba.data.io.PlayersListResponseIO
import retrofit2.Response
import retrofit2.http.GET
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
}