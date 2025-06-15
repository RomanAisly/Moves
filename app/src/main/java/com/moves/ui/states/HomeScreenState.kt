package com.moves.ui.states

import com.moves.domain.model.Films
import com.moves.utils.FilmsCategory
import com.moves.utils.FilmsLanguages

data class HomeScreenState(
    val films: List<Films> = emptyList(),
    val category: String = FilmsCategory.POPULAR,
    val language: String = FilmsLanguages.ENGLISH
)
