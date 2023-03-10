package com.example.nba.ui.player_detail

import com.example.nba.data.io.PlayerIO
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

/** Class responsible for saving all information related to player detail */
class PlayerDetailDataManager @Inject constructor() {

    /** Latest player that has been downloaded from either local database or RestApi */
    val player: MutableStateFlow<PlayerIO?> = MutableStateFlow(null)
}