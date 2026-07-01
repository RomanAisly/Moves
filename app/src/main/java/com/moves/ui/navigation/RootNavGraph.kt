package com.moves.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.moves.ui.screens.DetailsScreen
import com.moves.ui.screens.LanguageScreen


@Composable
fun RootNavGraph(rootNavHost: NavHostController) {
    NavHost(
        navController = rootNavHost,
        startDestination = Routes.BottomNavGraph
    ) {
        composable<Routes.BottomNavGraph> {
            BottomNavGraph(rootNavHost)
        }
        composable<Routes.Details> { backStackEntry ->
            val id = backStackEntry.toRoute<Routes.Details>().id
            DetailsScreen(filmId = id)
        }
        composable<Routes.SetLanguageScreen> { LanguageScreen() }
    }
}