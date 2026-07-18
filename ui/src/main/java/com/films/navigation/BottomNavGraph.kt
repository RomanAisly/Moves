package com.films.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import androidx.savedstate.serialization.SavedStateConfiguration
import com.films.screens.favorites.FavoritesScreen
import com.films.screens.home.HomeScreen
import com.films.screens.settings.SettingsScreen
import com.films.screens.watch_later.WatchLaterScreen
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic

@OptIn(ExperimentalSerializationApi::class)
@Composable
fun BottomNavGraph(
    onNavigateToDetails: (Int) -> Unit
) {
    val config = remember {
        SavedStateConfiguration {
            serializersModule = SerializersModule {
                polymorphic(NavKey::class) {
                    subclassesOfSealed<Routes>()
                }
            }
        }
    }

    val bottomBackStack = rememberNavBackStack(config, Routes.Home)
    val currentTab = bottomBackStack.last()

    BackHandler(enabled = currentTab != Routes.Home) {
        bottomBackStack.clear()
        bottomBackStack.add(Routes.Home)
    }

    Scaffold(
        bottomBar = {
            BottomNavBar(
                currentTab = currentTab,
                onTabSelected = { tabRoute ->
                    if (currentTab != tabRoute) {
                        bottomBackStack.clear()
                        bottomBackStack.add(tabRoute)
                    }
                }
            )
        }
    ) { paddingValues ->
        NavDisplay(
            backStack = bottomBackStack,
            modifier = Modifier
                .fillMaxSize(),
            entryProvider = entryProvider {
                entry<Routes.Home> {
                    HomeScreen(
                        paddingValues = paddingValues,
                        onFilmClick = onNavigateToDetails
                    )
                }

                entry<Routes.Favorites> {
                    FavoritesScreen(
                        paddingValues = paddingValues,
                        onFilmClick = onNavigateToDetails
                    )
                }

                entry<Routes.WatchLater> {
                    WatchLaterScreen(
                        paddingValues = paddingValues,
                        onFilmClick = onNavigateToDetails
                    )
                }

                entry<Routes.Settings> {
                    SettingsScreen(paddingValues = paddingValues)
                }
            })
    }
}
