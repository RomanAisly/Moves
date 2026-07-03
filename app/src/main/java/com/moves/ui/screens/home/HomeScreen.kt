package com.moves.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.moves.data.mappers.toLocalizedGenresString
import com.moves.ui.components.CustomSnaKBar
import com.moves.ui.components.FilmCategory
import com.moves.ui.components.FilmsItem
import com.moves.ui.components.LoadingScreen
import com.moves.ui.components.TabButton
import org.koin.androidx.compose.koinViewModel


@Composable
fun HomeScreen(
    viewModel: HomeScreenViewModel = koinViewModel(),
    onFilmClick: (id: Int) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .horizontalScroll(state = rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            FilmCategory.entries.forEach { category ->
                TabButton(
                    tabName = stringResource(category.nameRes),
                    tabColor = if (state.selectedCategory == category.category) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.primary,
                    onClick = {
                        viewModel.changeCategory(category)
                    }
                )
            }
        }

        if (state.films.isEmpty()) {
            LoadingScreen()
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(8.dp)
            ) {
                items(state.films, key = { it.id }) { films ->
                    FilmsItem(
                        modifier = Modifier,
                        poster = films.poster_path,
                        title = films.title,
                        genres = films.genreIds.toLocalizedGenresString(),
                        releaseDate = films.release_date,
                        rating = films.vote_average,
                        onFilmClick = { onFilmClick(films.id) })
                }
            }
        }
        CustomSnaKBar(snackBarFlow = viewModel.snack)
    }
}