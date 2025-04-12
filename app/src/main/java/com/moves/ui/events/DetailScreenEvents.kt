package com.moves.ui.events

sealed interface DetailScreenEvents {
    data class UpdateFavorite(val isFavorite: Boolean) : DetailScreenEvents
    data class UpdateWatchLater(val isWatchLater: Boolean) : DetailScreenEvents
}