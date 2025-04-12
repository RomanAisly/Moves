package com.moves.ui.events

import com.moves.domain.navigation.Screens

sealed interface HomeScreenEvents {
    data object ShowFilms : HomeScreenEvents
    data class ChangeCategory(val navigateTo: Screens) : HomeScreenEvents
}