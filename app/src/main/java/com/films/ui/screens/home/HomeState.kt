package com.films.ui.screens.home

import com.films.domain.model.Films
import com.films.ui.components.FilmCategory

data class HomeState(
    val films: List<Films> = emptyList(),
    val selectedCategory: String = FilmCategory.POPULAR.category,
    val isRefreshing: Boolean = false
)