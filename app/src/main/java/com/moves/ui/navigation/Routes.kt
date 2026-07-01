package com.moves.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Routes {

    @Serializable
    data object BottomNavGraph : Routes()

    @Serializable
    data object Home : Routes()

    @Serializable
    data object Favorites : Routes()

    @Serializable
    data object WatchLater : Routes()

    @Serializable
    data object Settings : Routes()

    @Serializable
    data class Details(val id: Int) : Routes()

    @Serializable
    data object SetLanguageScreen : Routes()
}