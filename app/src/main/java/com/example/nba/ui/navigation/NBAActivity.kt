package com.example.nba.ui.navigation

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.nba.R
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
            -1, 0,"LeBron", lastName = "James", heightFeet = 6, heightInches = 8, position = "F", weightPounds = 250, team = PREVIEW_DEFAULT_TEAM
        )
    }

    /** Returns screen display name based on the route identification */
    private fun getScreenNameResource(route: String?): Int? {
        return when {
            route?.startsWith(NavigationDestinations.PLAYERS_LIST) == true -> R.string.screen_name_players_list
            route?.startsWith(NavigationDestinations.PLAYER_DETAIL) == true -> R.string.screen_name_player_detail
            route?.startsWith(NavigationDestinations.TEAM_DETAIL) == true -> R.string.screen_name_team_detail
            else -> null
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NBATheme {
                val navController: NavHostController = rememberNavController()
                var canPop by remember { mutableStateOf(false) }
                var appBarTitleResource: Int? by remember { mutableStateOf(null) }
                navController.addOnDestinationChangedListener { controller, destination, _ ->
                    getScreenNameResource(destination.route)?.let {
                        appBarTitleResource = it
                    }
                    canPop = controller.previousBackStackEntry != null
                }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            colors = TopAppBarDefaults.mediumTopAppBarColors(
                                containerColor = MaterialTheme.colorScheme.primary
                            ),
                            title = {
                                appBarTitleResource?.let { resource ->
                                    Text(text = stringResource(id = resource))
                                }
                            },
                            navigationIcon = {
                                if(canPop) {
                                    IconButton(onClick = { navController.navigateUp() }) {
                                        Icon(
                                            imageVector = Icons.Filled.ArrowBack,
                                            contentDescription = "Go back"
                                        )
                                    }
                                }
                            }
                        )
                    }
                ) {
                    NBAAppNavHost(
                        modifier = Modifier.padding(top = it.calculateTopPadding()),
                        startDestination = NavigationDestinations.INITIAL_PAGE,
                        navController = navController
                    )
                }
            }
        }
    }
}

/** Main Nav Host container for navigation between all the pages */
@Composable
fun NBAAppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String
) {
    //TODO has to be like this for now - change in navController redraws the screen when leaving the screen,
    // which makes it flickr (haven't figured this out yet)
    val onItemClicked: (playerId: Long?) -> Unit = {
        navController.navigate(route = "${NavigationDestinations.PLAYER_DETAIL}/$it")
    }
    val onTeamClicked: (teamId: Long?) -> Unit = {
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
                onItemClicked = onItemClicked,
                onTeamClicked =  onTeamClicked
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
                onTeamClicked = onTeamClicked
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