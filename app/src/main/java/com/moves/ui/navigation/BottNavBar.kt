package com.moves.ui.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.LocalRippleConfiguration
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.moves.R
import com.moves.ui.components.BaseIcon
import com.moves.ui.theme.beige
import com.moves.ui.theme.blue
import com.moves.ui.theme.transparent
import com.moves.ui.theme.white

@Composable
fun BottomNavBar(bottomBarHost: NavController) {

    val bottomScreens = remember {
        listOf(
            BottomTabItem(
                route = Routes.Home,
                icon = R.drawable.home
            ),
            BottomTabItem(
                route = Routes.Favorites,
                icon = R.drawable.favorite
            ),
            BottomTabItem(
                route = Routes.WatchLater,
                icon = R.drawable.history
            ),
            BottomTabItem(
                route = Routes.Settings,
                icon = R.drawable.settings
            )
        )
    }

    NavigationBar(containerColor = MaterialTheme.colorScheme.surface) {
        val navBackStackEntry by bottomBarHost.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        bottomScreens.forEach { item ->
            val isSelected = currentDestination?.hasRoute(item.route::class) == true

            CompositionLocalProvider(LocalRippleConfiguration provides null) {
                NavigationBarItem(
                    selected = isSelected,
                    onClick = {
                        bottomBarHost.navigate(item.route) {
                            popUpTo(bottomBarHost.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    icon = {
                        Box(
                            modifier = Modifier
                                .size(44.dp)
                                .background(
                                    color = if (isSelected) beige else transparent,
                                    shape = CircleShape
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            BaseIcon(
                                iconId = item.icon,
                                iconTint = if (isSelected) MaterialTheme.colorScheme.onSurface else white
                            )
                        }

                    },
                    colors = NavigationBarItemDefaults.colors(
                        indicatorColor = transparent
                    )
                )
            }
        }

    }
}
data class BottomTabItem(
    val route: Routes,
    val icon: Int
)
