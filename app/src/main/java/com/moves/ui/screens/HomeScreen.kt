package com.moves.ui.screens

import android.widget.Toast
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.moves.R
import com.moves.domain.model.Films
import com.moves.ui.components.CategoryButton
import com.moves.ui.components.FilmsItem
import com.moves.ui.components.LoadingScreen
import com.moves.ui.events.HomeScreenEvents
import com.moves.ui.viewmodels.HomeScreenViewModel
import com.moves.utils.FilmsCategory
import org.koin.androidx.compose.koinViewModel


@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeScreenViewModel = koinViewModel(),
    onFilmClick: (id: Films) -> Unit
) {

    val context = LocalContext.current
    val state by viewModel.state.collectAsState()

    Column(modifier = modifier.fillMaxSize()) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(8.dp)
                .horizontalScroll(state = rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            CategoryButton(
                changeCategory = {
                    viewModel.onEvent(
                        HomeScreenEvents.ChangeCategory(
                            FilmsCategory.POPULAR
                        )
                    )
                },
                categoryName = stringResource(R.string.film_category_popular)
            )
            CategoryButton(
                changeCategory = {
                    viewModel.onEvent(
                        HomeScreenEvents.ChangeCategory(
                            FilmsCategory.NOW_PLAYING
                        )
                    )
                },
                categoryName = stringResource(R.string.film_category_now_playing)
            )
            CategoryButton(
                changeCategory = {
                    viewModel.onEvent(
                        HomeScreenEvents.ChangeCategory(
                            FilmsCategory.TOP
                        )
                    )
                },
                categoryName = stringResource(R.string.film_category_top)
            )
            CategoryButton(
                changeCategory = {
                    viewModel.onEvent(
                        HomeScreenEvents.ChangeCategory(
                            FilmsCategory.UPCOMING
                        )
                    )
                },
                categoryName = stringResource(R.string.film_category_upcoming)
            )
        }

        if (state.films.isEmpty()) {
            LoadingScreen()
            LaunchedEffect(viewModel.toast) {
                viewModel.toast.collect {
                    Toast.makeText(context, R.string.toast, Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2)
            ) {
                items(state.films.size) { index ->
                    FilmsItem(
                        films = state.films[index],
                        modifier = modifier,
                        onFilmClick = { id -> onFilmClick(id) })
                }
            }
        }
    }
}