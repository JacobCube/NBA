package com.example.nba.ui.players_list

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.nba.data.PlayersRestApi
import com.example.nba.data.io.PlayersListResponseIO
import com.example.nba.data.room.AppRoomDatabase
import com.example.nba.data.room.PlayersRemoteMediator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Repository for requesting the NBA API
 */
class PlayersListRepository @Inject constructor(
     private val restApi: PlayersRestApi,
     private val roomDatabase: AppRoomDatabase
) {
    /**
     * Returns paginated list of players
     * @param pagingConfig config for Paging Source
     */
    @OptIn(ExperimentalPagingApi::class)
    fun getPagingList(pagingConfig: PagingConfig) = Pager(
        config = pagingConfig,
        pagingSourceFactory = {
            roomDatabase.playerDbDao().getAllPlayers()
        },
        remoteMediator = PlayersRemoteMediator(
            roomDatabase,
            0,
            pagingConfig.pageSize
        ) { pageIndex, perPage ->
            getPlayerListPage(pageIndex, perPage)
        }
    ).flow

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
            ).body()?.also {
                roomDatabase.playerDbDao().insertAllPlayers(it.data)
            }
        }
    }
}