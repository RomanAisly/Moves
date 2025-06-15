package com.moves.ui.events

sealed interface HomeScreenEvents {
    data object ShowFilms : HomeScreenEvents
    data class ChangeCategory(val category: String) : HomeScreenEvents
}