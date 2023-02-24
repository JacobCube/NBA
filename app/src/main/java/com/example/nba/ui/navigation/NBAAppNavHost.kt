package com.example.nba.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.example.nba.ui.player_detail.ScreenPlayerDetail
import com.example.nba.ui.players_list.ScreenPlayersList
import com.example.nba.ui.team_detail.ScreenTeamDetail

/** Main Nav Host container for navigation between all the pages */
@Composable
fun NBAAppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String
) {
    //TODO has to be like this for now - change in navController redraws the screen when leaving the screen,
    // which makes it flickr
    val onPlayerItemClicked: (playerId: Long?) -> Unit = {
        navController.navigate(route = "${NavigationDestinations.PLAYER_DETAIL}/$it")
    }
    val onTeamItemClicked: (teamId: Long?) -> Unit = {
        navController.navigate(route = "${NavigationDestinations.TEAM_DETAIL}/$it")
    }
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {

        /** Full paginated list of NBA players */
        composable(NavigationDestinations.PLAYERS_LIST) {
            ScreenPlayersList(
                onItemClicked = onPlayerItemClicked,
                onTeamClicked = onTeamItemClicked
            )
        }

        /** Detail of an NBA player based off [PlayerIO] argument object or an identifier */
        composable(
            "${NavigationDestinations.PLAYER_DETAIL}/{${NavigationDestinations.ARGUMENT_PLAYER_ID}}",
            deepLinks = listOf(
                navDeepLink { uriPattern = "${NavigationDestinations.PLAYER_DETAIL}/{${NavigationDestinations.ARGUMENT_PLAYER_ID}}" }
            ),
            arguments = listOf(
                navArgument(NavigationDestinations.ARGUMENT_PLAYER_ID) {
                    type = NavType.StringType
                    nullable = true
                    defaultValue = ""
                }
            )
        ) { backStackEntry ->
            ScreenPlayerDetail(
                playerId = backStackEntry.arguments?.getString(NavigationDestinations.ARGUMENT_PLAYER_ID),
                onTeamClicked = onTeamItemClicked
            )
        }

        /** Detail of an NBA team based off [PlayerTeamIO] argument object or an identifier */
        composable(
            "${NavigationDestinations.TEAM_DETAIL}/{${NavigationDestinations.ARGUMENT_PLAYER_TEAM_ID}}",
            arguments = listOf(
                navArgument(NavigationDestinations.ARGUMENT_PLAYER_TEAM_ID) {
                    nullable = true
                    defaultValue = null
                }
            ),
            deepLinks = listOf(
                navDeepLink { uriPattern = "${NavigationDestinations.TEAM_DETAIL}/{${NavigationDestinations.ARGUMENT_PLAYER_TEAM_ID}}" }
            )
        ) { backStackEntry ->
            ScreenTeamDetail(
                teamId = backStackEntry.arguments?.getString(NavigationDestinations.ARGUMENT_PLAYER_ID)
            )
        }
    }
}