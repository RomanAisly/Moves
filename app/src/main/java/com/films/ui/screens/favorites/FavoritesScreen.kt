package com.films.ui.screens.favorites

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.films.R
import com.films.data.mappers.toLocalizedGenresString
import com.films.ui.components.BaseText
import com.films.ui.components.FilmsItem
import com.films.ui.theme.AppTheme
import com.films.ui.theme.lightPink
import org.koin.androidx.compose.koinViewModel

@Composable
fun FavoritesScreen(
    viewModel: FavoritesViewModel = koinViewModel(),
    paddingValues: PaddingValues,
    onFilmClick: (id: Int) -> Unit
) {
    val favoriteFilms by viewModel.favoriteFilms.collectAsStateWithLifecycle()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AppTheme.colors.screenBack)
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp),
            contentPadding = PaddingValues(
                start = 8.dp,
                end = 8.dp,
                top = paddingValues.calculateTopPadding() + 58.dp,
                bottom = 8.dp + paddingValues.calculateBottomPadding() + 8.dp
            )
        ) {
            items(favoriteFilms, key = { it.id }) { films ->
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
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(bottomStart = 14.dp, bottomEnd = 14.dp))
                .background(AppTheme.colors.bottomBar)
                .padding(top = paddingValues.calculateTopPadding()),
            contentAlignment = Alignment.Center
        ) {
            BaseText(
                text = stringResource(R.string.favorites),
                textStyle = MaterialTheme.typography.headlineLarge,
                textColor = lightPink,
                modifier = Modifier.padding(bottom = 14.dp, top = 8.dp)
            )
        }
    }
}