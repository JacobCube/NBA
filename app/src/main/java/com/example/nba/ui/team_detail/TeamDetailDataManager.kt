package com.example.nba.ui.team_detail

import com.example.nba.data.io.PlayerTeamIO
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

/** Class responsible for saving all information related to team detail */
class TeamDetailDataManager @Inject constructor() {

    /** Latest team that has been downloaded from either local database or RestApi */
    val team: MutableStateFlow<PlayerTeamIO?> = MutableStateFlow(null)
}