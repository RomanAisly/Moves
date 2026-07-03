package com.moves.ui.screens.details

import com.moves.domain.model.Films

data class DetailsScreenState(
    val filmDetails: Films? = null,
    val isFavorite: Boolean = false,
    val isWatchLater: Boolean = false
)