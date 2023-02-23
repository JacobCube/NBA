package com.example.nba.ui.navigation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.example.nba.data.io.PlayerIO
import com.example.nba.data.io.PlayerTeamIO
import com.example.nba.ui.player_detail.ScreenPlayerDetail
import com.example.nba.ui.players_list.ScreenPlayersList
import com.example.nba.ui.team_detail.ScreenTeamDetail
import com.example.nba.ui.theme.NBATheme
import dagger.hilt.android.AndroidEntryPoint

/**
 * Main container activity
 */
@AndroidEntryPoint
class NBAActivity: ComponentActivity() {

    companion object {
        /** Default team object for purposes of testing */
        private val PREVIEW_DEFAULT_TEAM = PlayerTeamIO(name = "Lakers", city = "Los Angeles")

        /** Default player object for purposes of testing */
        val PREVIEW_DEFAULT_PLAYER = PlayerIO(
            -1, "LeBron", lastName = "James", heightFeet = 6, heightInches = 8, position = "F", weightPounds = 250, team = PREVIEW_DEFAULT_TEAM
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NBATheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NBAAppNavHost(startDestination = NavigationDestinations.INITIAL_PAGE)
                }
            }
        }
    }
}

/** Main Nav Host container for navigation between all the pages */
@Composable
fun NBAAppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {

        /** Full pagable list of NBA players */
        composable(NavigationDestinations.PLAYERS_LIST) {
            ScreenPlayersList(
                onItemClicked = {
                    navController.navigate(route = "${NavigationDestinations.PLAYER_DETAIL}/${it.id}")
                },
                onTeamClicked =  {
                    navController.navigate(route = "${NavigationDestinations.TEAM_DETAIL}/${it.id}")
                }
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
                playerId = backStackEntry.arguments?.getString(NavigationDestinations.ARGUMENT_PLAYER_ID)
            ) {
                navController.navigate(route = "${NavigationDestinations.TEAM_DETAIL}/${it.id}")
            }
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
                backStackEntry.arguments?.getString(NavigationDestinations.ARGUMENT_PLAYER_ID)
            )
        }
    }
}