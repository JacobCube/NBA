package com.example.nba.ui.player_detail

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.nba.data.PlayersRestApi
import com.example.nba.data.io.PlayersListResponseIO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Repository for requesting the NBA API
 */
class PlayerDetailRepository @Inject constructor(
     private val restApi: PlayersRestApi
) {

    /**
     * Request for a specific page of players
     * @param pageIndex index of page that should be retrieved
     * @param perPage how many items should there be per page
     */
    private suspend fun getPlayerListPage(
        pageIndex: Int,
        perPage: Int
    ): PlayersListResponseIO? {
        return withContext(Dispatchers.IO) {
            restApi.getPlayersListPage(
                pageIndex = pageIndex,
                perPage = perPage
            ).body()
        }
    }
}