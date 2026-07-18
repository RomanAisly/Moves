package com.films.screens.details

import com.films.Films
import com.films.WatchProvider

data class DetailsState(
    val filmDetails: Films? = null,
    val isFavorite: Boolean = false,
    val isWatchLater: Boolean = false,
    val trailerKey: String? = null,
    val watchProviders: List<WatchProvider> = emptyList()
)