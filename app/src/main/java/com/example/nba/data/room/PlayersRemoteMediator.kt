package com.example.nba.data.room

import androidx.paging.*
import androidx.room.withTransaction
import com.example.nba.data.io.PagingMetaIO
import com.example.nba.data.io.PlayerIO
import com.example.nba.data.io.PlayersListResponseIO
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.TimeUnit

/**
 * Mediator works in between local Room database and RestApi data requests,
 * in other words it combines the two services
 */
@OptIn(ExperimentalPagingApi::class)
class PlayersRemoteMediator (
    private val roomDatabase: AppRoomDatabase,
    private val initialPage: Int,
    private val perPage: Int,
    private val getPlayerListPage: suspend (pageIndex: Int, perPage: Int) -> PlayersListResponseIO?
): RemoteMediator<Int, PlayerIO>() {

    override suspend fun initialize(): InitializeAction {
        val cacheTimeout = TimeUnit.MILLISECONDS.convert(1, TimeUnit.HOURS)

        return if (System.currentTimeMillis() - (roomDatabase.pagingMetaDbDao().getCreationTime() ?: 0) < cacheTimeout) {
            InitializeAction.SKIP_INITIAL_REFRESH
        } else {
            InitializeAction.LAUNCH_INITIAL_REFRESH
        }
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PlayerIO>
    ): MediatorResult {
        val page: Int = when (loadType) {
            LoadType.REFRESH -> {
                getRemoteKeyClosestToCurrentPosition(state)?.nextPage ?: initialPage
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                val prevKey = if((remoteKeys?.currentPage ?: initialPage) > 0) remoteKeys?.currentPage?.minus(1) else null
                prevKey ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                remoteKeys?.nextPage ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
            }
        }

        try {
            val apiResponse = getPlayerListPage.invoke(page, perPage)

            val players = apiResponse?.data
            val endOfPaginationReached = players?.isEmpty() != false

            roomDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {

                    roomDatabase.pagingMetaDbDao().removePagingMeta()
                    roomDatabase.playerDbDao().removeAllPlayers()
                }
                val prevKey = if (page > 1) page - 1 else null
                val nextKey = if (endOfPaginationReached) null else page + 1
                players?.map {
                    PagingMetaIO(playerId = it.id, previousPage = prevKey, currentPage = page, nextPage = nextKey)
                }?.let {
                    roomDatabase.pagingMetaDbDao().insertAll(it)
                }
                roomDatabase.playerDbDao().insertAllPlayers(players?.onEachIndexed { _, player -> player.page = page }.orEmpty())
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (error: IOException) {
            return MediatorResult.Error(error)
        } catch (error: HttpException) {
            return MediatorResult.Error(error)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, PlayerIO>): PagingMetaIO? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                roomDatabase.pagingMetaDbDao().getPagingMetaByPlayerId(id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, PlayerIO>): PagingMetaIO? {
        return state.pages.firstOrNull {
            it.data.isNotEmpty()
        }?.data?.firstOrNull()?.id?.let { id ->
            roomDatabase.pagingMetaDbDao().getPagingMetaByPlayerId(id)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, PlayerIO>): PagingMetaIO? {
        return state.pages.lastOrNull {
            it.data.isNotEmpty()
        }?.data?.lastOrNull()?.id?.let { id ->
            roomDatabase.pagingMetaDbDao().getPagingMetaByPlayerId(id)
        }
    }
}