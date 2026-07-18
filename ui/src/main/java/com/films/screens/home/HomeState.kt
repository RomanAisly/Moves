package com.films.screens.home

import com.films.Films
import com.films.components.FilmCategory

data class HomeState(
    val films: List<Films> = emptyList(),
    val selectedCategory: String = FilmCategory.POPULAR.category,
    val isRefreshing: Boolean = false
)