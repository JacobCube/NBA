package com.example.nba.ui.navigation

/** Identifications of navigation directions */
object NavigationDestinations {

    /** Identification of page for list of players */
    const val PLAYERS_LIST = "nba_players_list"

    /** Identification of page for detail of a player */
    const val PLAYER_DETAIL = "nba_player_detail"

    /** Identification of page for detail of a team */
    const val TEAM_DETAIL = "nba_team_detail"

    /** Initial page identifier */
    const val INITIAL_PAGE = PLAYERS_LIST

    /** argument identifier for [PlayerIO] serializable */
    const val ARGUMENT_PLAYER_IO = "playerIO"

    /** argument identifier for identifier of a Player */
    const val ARGUMENT_PLAYER_ID = "playerId"

    /** argument identifier for [PlayerTeamIO] serializable */
    const val ARGUMENT_PLAYER_TEAM_IO = "playerTeamIO"

    /** argument identifier for identifier of a Team */
    const val ARGUMENT_PLAYER_TEAM_ID = "playerId"
}