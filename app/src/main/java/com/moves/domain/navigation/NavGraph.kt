package com.moves.domain.navigation

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.moves.ui.screens.DetailsScreen
import com.moves.ui.screens.FavoritesScreen
import com.moves.ui.screens.HomeScreen
import com.moves.ui.screens.SettingsScreen
import com.moves.ui.screens.WatchLaterScreen


@Composable
fun NavGraph(modifier: Modifier = Modifier, navHostController: NavHostController) {
    Scaffold(
        contentColor = MaterialTheme.colorScheme.background,
        bottomBar = { BottomNavBar(navHostController) }
    ) { innerPadding ->
        NavHost(
            modifier = modifier
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.background),
            navController = navHostController,
            startDestination = Screens.Home,
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { 900 },
                    animationSpec = tween(
                        durationMillis = 1200,
                        easing = FastOutSlowInEasing
                    )
                )
            },
            exitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { 900 },
                    animationSpec = tween(
                        durationMillis = 1200,
                        easing = FastOutSlowInEasing
                    )
                )
            }
        ) {
            composable<Screens.Home> { HomeScreen(navigateTo = { navHostController.navigate(it) }) }
            composable<Screens.Favorites> { FavoritesScreen() }
            composable<Screens.WatchLater> { WatchLaterScreen() }
            composable<Screens.Settings> { SettingsScreen() }
            composable<Screens.Details> { DetailsScreen() }
        }
    }
}