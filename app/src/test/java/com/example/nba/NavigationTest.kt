package com.example.nba

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.example.nba.ui.navigation.NBAAppNavHost
import com.example.nba.ui.navigation.NavigationDestinations
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/** Place where we can test navigation */
class NavigationTest {

    @get:Rule
    val composeTestRule = createComposeRule()
    lateinit var navController: TestNavHostController

    @Before
    fun setupAppNavHost() {
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            NBAAppNavHost(navController = navController, startDestination = NavigationDestinations.INITIAL_PAGE)
        }
    }

    // Unit test
    @Test
    fun appNavHostVerifyStartDestination() {
        composeTestRule
            .onNodeWithContentDescription(NavigationDestinations.INITIAL_PAGE)
            .assertIsDisplayed()
    }
}
