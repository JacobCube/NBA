package com.example.nba.ui.player_detail

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
class PlayerDetailViewModel @Inject constructor(
    private val repository: PlayerDetailRepository,
    val dataManager: PlayerDetailDataManager
): ViewModel() {

}