package com.films.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.films.screens.favorites.FavoritesScreen
import com.films.screens.home.HomeScreen
import com.films.screens.settings.SettingsScreen
import com.films.screens.watch_later.WatchLaterScreen

@Composable
fun BottomNavGraph(
    bottomNavHost: NavController
) {
    val bottomNavController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomNavBar(
                bottomBarHost = bottomNavController
            )
        }
    ) { paddingValues ->
        NavHost(
            navController = bottomNavController,
            startDestination = Routes.Home,
            modifier = Modifier
                .fillMaxSize()
        ) {
            composable<Routes.Home> {
                HomeScreen(paddingValues = paddingValues, onFilmClick = { id ->
                    bottomNavHost.navigate(Routes.Details(id))
                })
            }
            composable<Routes.Favorites> {
                FavoritesScreen(paddingValues = paddingValues, onFilmClick = { id ->
                    bottomNavHost.navigate(Routes.Details(id))
                })
            }
            composable<Routes.WatchLater> {
                WatchLaterScreen(paddingValues = paddingValues, onFilmClick = { id ->
                    bottomNavHost.navigate(Routes.Details(id))
                })
            }
            composable<Routes.Settings> { SettingsScreen(paddingValues) }
        }
    }
}
