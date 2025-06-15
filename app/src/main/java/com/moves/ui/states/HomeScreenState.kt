package com.moves.ui.states

import com.moves.domain.model.Films
import com.moves.utils.FilmsCategory

data class HomeScreenState(
    val films: List<Films> = emptyList(),
    val category: String = FilmsCategory.POPULAR,
    val isRefreshing: Boolean = false
)
