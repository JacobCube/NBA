package com.example.nba.ui.players_list

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.example.nba.data.io.PlayerIO
import com.example.nba.data.io.PlayerTeamIO

/**
 * Screen of a paginated list of all the NBA players
 */
@Preview(showBackground = true)
@Composable
fun ScreenPlayersList(
    onItemClicked: (playerIO: PlayerIO) -> Unit = {},
    onTeamClicked: (teamIO: PlayerTeamIO) -> Unit = {},
) {
    val viewModel = hiltViewModel<PlayersListViewModel>()
    val playersList = viewModel.requestPlayersList().collectAsLazyPagingItems()

    LazyColumn {
        items(
            items = playersList
        ) { player ->
            if(player != null) {
                PlayerItem(playerIO = player, onItemClicked = onItemClicked, onTeamClicked = onTeamClicked)
            }else {
                PlayerItemShimmer()
            }
            Divider(
                modifier = Modifier.padding(horizontal = 8.dp),
                thickness = 0.5.dp,
                color = MaterialTheme.colorScheme.secondary
            )
        }
    }
}