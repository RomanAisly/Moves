package com.films.ui.screens.details

import com.films.domain.model.Films

data class DetailsScreenState(
    val filmDetails: Films? = null,
    val isFavorite: Boolean = false,
    val isWatchLater: Boolean = false
)