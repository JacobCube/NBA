package com.example.nba.ui.team_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for connecting team detail to its equivalent API
 */
@HiltViewModel
class TeamDetailViewModel @Inject constructor(
    private val repository: TeamDetailRepository,
    val dataManager: TeamDetailDataManager
): ViewModel() {

    /**
     * Makes a request for a specific NBA team
     * @param teamId team identification
     */
    fun getTeamById(teamId: String) {
        viewModelScope.launch {
            repository.getTeamById(teamId)?.let {
                dataManager.team.value = it
            }
        }
    }
}