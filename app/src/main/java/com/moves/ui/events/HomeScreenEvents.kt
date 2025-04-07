package com.moves.ui.events

sealed interface HomeScreenEvents {
    data object ShowFilms : HomeScreenEvents
}