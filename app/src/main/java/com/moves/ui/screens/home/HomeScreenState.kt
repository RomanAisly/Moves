package com.moves.ui.screens.home

import com.moves.domain.model.Films
import com.moves.ui.components.FilmCategory

data class HomeScreenState(
    val films: List<Films> = emptyList(),
    val selectedCategory: String = FilmCategory.POPULAR.category
)