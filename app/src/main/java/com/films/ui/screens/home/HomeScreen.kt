package com.films.ui.screens.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.films.data.mappers.toLocalizedGenresString
import com.films.ui.components.FilmCategory
import com.films.ui.components.FilmsItem
import com.films.ui.components.LoadingScreen
import com.films.ui.components.RefreshIndicator
import com.films.ui.components.SnackBarFlow
import com.films.ui.components.TabButton
import com.films.ui.theme.AppTheme
import org.koin.androidx.compose.koinViewModel


@Composable
fun HomeScreen(
    viewModel: HomeViewModel = koinViewModel(),
    paddingValues: PaddingValues,
    onFilmClick: (id: Int) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val refreshState = rememberPullToRefreshState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AppTheme.colors.screenBack)
    ) {
        RefreshIndicator(
            state = refreshState,
            isRefreshing = state.isRefreshing,
            paddingValues = paddingValues,
            onRefresh = { viewModel.refreshFilms() }
        ) {
            if (state.films.isEmpty() && !state.isRefreshing) {
                LoadingScreen()
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp),
                    contentPadding = PaddingValues(
                        start = 8.dp,
                        end = 8.dp,
                        top = paddingValues.calculateTopPadding() + 72.dp,
                        bottom = 8.dp + paddingValues.calculateBottomPadding()
                    )
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
        }
        Row(
            modifier = Modifier
                .clip(
                    shape = RoundedCornerShape(
                        bottomStart = 14.dp,
                        bottomEnd = 14.dp
                    )
                )
                .background(AppTheme.colors.bottomBar)
                .padding(top = paddingValues.calculateTopPadding())
                .fillMaxWidth()
                .padding(8.dp)
                .horizontalScroll(state = rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            FilmCategory.entries.forEach { category ->
                TabButton(
                    tabName = stringResource(category.nameRes),
                    tabColor = if (state.selectedCategory == category.category) AppTheme.colors.buttTertiary else AppTheme.colors.buttSecondary,
                    border = if (state.selectedCategory == category.category) BorderStroke(
                        2.dp,
                        AppTheme.colors.border
                    ) else null,
                    onClick = {
                        viewModel.changeCategory(category)
                    }
                )
            }
        }
        SnackBarFlow(
            snackBarFlow = viewModel.snack,
            iconRes = { it.iconRes },
            messageRes = { it.messageRes },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = paddingValues.calculateBottomPadding())
        )
    }
}