package com.example.nba.ui.team_detail

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.nba.data.io.PlayerTeamIO
import com.example.nba.ui.navigation.NBAActivity.Companion.PREVIEW_DEFAULT_PLAYER

/**
 * Screen of a team
 * @param teamId team identifier by which we can download the necessary data
 */
@Preview(showBackground = true)
@Composable
fun ScreenTeamDetail(
    teamId: String? = null,
) {
    val viewModel = hiltViewModel<TeamDetailViewModel>()


}