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
import androidx.navigation.compose.rememberNavController
import com.example.nba.data.io.PlayerIO
import com.example.nba.data.io.PlayerTeamIO
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