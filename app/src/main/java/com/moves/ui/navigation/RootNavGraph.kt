package com.moves.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.moves.ui.screens.details.DetailsScreen
import com.moves.ui.screens.settings.LanguageScreen


@Composable
fun RootNavGraph(rootNavHost: NavHostController) {
    NavHost(
        navController = rootNavHost,
        startDestination = Routes.BottomNavGraph
    ) {
        composable<Routes.BottomNavGraph> {
            BottomNavGraph(rootNavHost)
        }
        composable<Routes.Details> {
            DetailsScreen()
        }
        composable<Routes.SetLanguageScreen> { LanguageScreen() }
    }
}