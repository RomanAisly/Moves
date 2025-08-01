package com.moves.domain.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Screens {

    @Serializable
    data object Home : Screens()

    @Serializable
    data object Favorites : Screens()

    @Serializable
    data object WatchLater : Screens()

    @Serializable
    data object Settings : Screens()

    @Serializable
    data class Details(val id: Int) : Screens()

    @Serializable
    data object SetLanguageScreen: Screens()
}