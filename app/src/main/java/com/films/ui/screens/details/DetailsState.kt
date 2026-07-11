package com.films.ui.screens.details

import com.films.domain.model.Films
import com.films.domain.model.WatchProvider

data class DetailsState(
    val filmDetails: Films? = null,
    val isFavorite: Boolean = false,
    val isWatchLater: Boolean = false,
    val trailerKey: String? = null,
    val watchProviders: List<WatchProvider> = emptyList()
)