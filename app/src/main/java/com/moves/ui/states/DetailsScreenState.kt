package com.moves.ui.states

import com.moves.domain.model.Films

data class DetailsScreenState(
    val filmDetails: Films? = null,
    val isFavorite: Boolean = false,
    val isWatchLater: Boolean = false
)
