package com.example.nba.ui.player_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for connecting player detail to its equivalent API
 */
@HiltViewModel
class PlayerDetailViewModel @Inject constructor(
    private val repository: PlayerDetailRepository,
    val dataManager: PlayerDetailDataManager
): ViewModel() {

    /**
     * Makes a request for a specific NBA player
     * @param playerId player identification
     */
    fun getPlayerById(playerId: String) {
        viewModelScope.launch {
            repository.getPlayerById(playerId)?.let {
                dataManager.player.value = it
            }
        }
    }
}