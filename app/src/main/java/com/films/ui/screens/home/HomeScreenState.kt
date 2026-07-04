package com.films.ui.screens.home

import com.films.domain.model.Films
import com.films.ui.components.FilmCategory

data class HomeScreenState(
    val films: List<Films> = emptyList(),
    val selectedCategory: String = FilmCategory.POPULAR.category
)