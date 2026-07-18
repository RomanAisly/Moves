package com.films.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.films.screens.details.DetailsScreen


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
    }
}