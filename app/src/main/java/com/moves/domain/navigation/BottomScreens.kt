package com.moves.domain.navigation

import androidx.annotation.StringRes
import com.moves.R
import kotlinx.serialization.Serializable

@Serializable
sealed class BottomScreens<T>(@StringRes val label: Int, val icon: Int, val route: T) {
    @Serializable
    data object Home : BottomScreens<Screens.Home>(
        R.string.bott_nav_item_home,
        R.drawable.ic_home,
        Screens.Home
    )

    @Serializable
    data object Favorites : BottomScreens<Screens.Favorites>(
        R.string.bott_nav_item_favorites,
        R.drawable.ic_favorite_fill,
        Screens.Favorites
    )

    @Serializable
    data object WatchLater : BottomScreens<Screens.WatchLater>(
        R.string.bott_nav_item_watchlater,
        R.drawable.ic_watch_later,
        Screens.WatchLater
    )

    @Serializable
    data object Settings : BottomScreens<Screens.Settings>(
        R.string.bott_nav_item_settings,
        R.drawable.ic_settings,
        Screens.Settings
    )
}