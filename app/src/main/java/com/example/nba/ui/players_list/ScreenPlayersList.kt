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

/**
 * Screen of a paginated list of all the NBA players
 */
@Preview(showBackground = true)
@Composable
fun ScreenPlayersList(
    modifier: Modifier = Modifier,
    onItemClicked: (playerId: Long?) -> Unit = {},
    onTeamClicked: (teamId: Long?) -> Unit = {},
) {
    val viewModel = hiltViewModel<PlayersListViewModel>()
    val playersList = viewModel.requestPlayersList().collectAsLazyPagingItems()

    LazyColumn(modifier) {
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