package com.example.nba.ui.players_list

import com.example.nba.data.io.PlayerIO
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

/** Class responsible for saving all information related to player detail */
class PlayerListDataManager @Inject constructor() {

    /** Latest player list that has been downloaded from either local database or RestApi */
    val player: MutableStateFlow<PlayerIO?> = MutableStateFlow(null)
}