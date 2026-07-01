package com.moves.ui.states.bottom

import com.moves.domain.model.Films
import com.moves.ui.components.FilmCategory

data class HomeScreenState(
    val films: List<Films> = emptyList(),
    val category: String = FilmCategory.POPULAR.category
)