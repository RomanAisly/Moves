package com.moves.ui.states

import com.moves.domain.model.Films

data class HomeScreenState(
    val films: List<Films> = emptyList()
)
