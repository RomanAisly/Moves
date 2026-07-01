package com.moves.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.moves.ui.screens.bottom.FavoritesScreen
import com.moves.ui.screens.bottom.HomeScreen
import com.moves.ui.screens.bottom.SettingsScreen
import com.moves.ui.screens.bottom.WatchLaterScreen

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
            modifier = Modifier.fillMaxSize().padding(paddingValues)
        ) {
            composable<Routes.Home> {
                HomeScreen(onFilmClick = { id ->
                    bottomNavHost.navigate(Routes.Details(id))
                })
            }
            composable<Routes.Favorites> { FavoritesScreen() }
            composable<Routes.WatchLater> { WatchLaterScreen() }
            composable<Routes.Settings> { SettingsScreen() }
        }
    }
}
