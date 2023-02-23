package com.example.nba.ui.players_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * ViewModel for connecting list of players to its equivalent API
 */
@HiltViewModel
class PlayersListViewModel @Inject constructor(
    private val repository: PlayersListRepository
): ViewModel() {

    companion object {
        private const val DEFAULT_PAGE_SIZE = 20
    }

    private val pagingConfig = PagingConfig(
        //TODO dynamic page size based on ViewHolder height and display height
        pageSize = DEFAULT_PAGE_SIZE,
        enablePlaceholders = true,
        initialLoadSize = DEFAULT_PAGE_SIZE.times(2)
    )

    /**
     * Creates a new PagingSource and requests the first pages,
     * depending on the config
     */
    fun requestPlayersList() = repository.getPagingList(pagingConfig).cachedIn(viewModelScope)
}