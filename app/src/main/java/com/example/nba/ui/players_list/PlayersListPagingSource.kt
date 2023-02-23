package com.example.nba.ui.players_list

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.nba.data.io.PlayerIO
import com.example.nba.data.io.PlayersListResponseIO
import retrofit2.HttpException
import java.io.IOException

/**
 * Factory for PagingData which will in turn produce pages with players
 * @param perPage how many items should there be per page
 * @param initialPage on what index does the data set start
 * @param getPlayerListPage callback for getting a page from API
 */
class PlayersListPagingSource(
    private val perPage: Int,
    private val initialPage: Int = 0,
    private val getPlayerListPage: suspend (pageIndex: Int, perPage: Int) -> PlayersListResponseIO?
): PagingSource<Int, PlayerIO>() {

    override fun getRefreshKey(state: PagingState<Int, PlayerIO>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PlayerIO> {
        return try {
            val page = params.key ?: initialPage
            val response = getPlayerListPage(page, perPage)

            LoadResult.Page(
                data = response?.data.orEmpty(),
                itemsBefore = if(page == 0) 0 else perPage,
                prevKey = if(page == 0) null else page.minus(1),
                nextKey = response?.meta?.nextPage,
                itemsAfter = if(response?.meta?.nextPage != null) perPage else 0
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }
}