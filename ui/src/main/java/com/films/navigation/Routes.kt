package com.films.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed interface Routes : NavKey {

    @Serializable
    data object BottomNavGraph : Routes

    @Serializable
    data object Home : Routes

    @Serializable
    data object Favorites : Routes

    @Serializable
    data object WatchLater : Routes

    @Serializable
    data object Settings : Routes

    @Serializable
    data class Details(val id: Int) : Routes
}