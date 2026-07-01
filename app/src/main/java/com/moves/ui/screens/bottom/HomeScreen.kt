package com.moves.ui.screens.bottom

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.moves.R
import com.moves.domain.di.previewModule
import com.moves.ui.components.FilmCategory
import com.moves.ui.components.FilmsItem
import com.moves.ui.components.LoadingScreen
import com.moves.ui.components.TabButton
import com.moves.ui.navigation.BottomNavGraph
import com.moves.ui.theme.FilmsTheme
import com.moves.ui.theme.lightYellow
import com.moves.ui.theme.twilight
import com.moves.ui.theme.white
import com.moves.ui.viewmodels.bottom.HomeScreenViewModel
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.KoinContext
import org.koin.dsl.koinApplication


@Composable
fun HomeScreen(
    viewModel: HomeScreenViewModel = koinViewModel(),
    onFilmClick: (id: Int) -> Unit
) {

    val context = LocalContext.current
    val state by viewModel.state.collectAsState()

    Column(modifier = Modifier
        .fillMaxSize()
        .background(lightYellow)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(state = rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            FilmCategory.entries.forEach { category ->
                TabButton(
                    tabName = stringResource(category.nameRes),
                    tabColor = if (state.category == category.category) white else category.colorRes,
                    onClick = {
//                        viewModel.changeCategory(category.category)
                    }
                )
            }
        }

        LaunchedEffect(key1 = Unit) {
            viewModel.toast.collect {
                Toast.makeText(context, R.string.toast, Toast.LENGTH_SHORT).show()
            }
        }

        if (state.films.isEmpty()) {
            LoadingScreen()

        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2)
            ) {
                items(state.films, key = { it.id }) { films ->
                    FilmsItem(
                        films = films,
                        onFilmClick = { onFilmClick(films.id) })
                }
            }
        }
    }
}

@Composable
@PreviewLightDark
@Preview(showBackground = true, showSystemUi = true)
private fun Preview() {
    val koin = remember {
        koinApplication {
            modules(previewModule)
        }.koin
    }
    KoinContext(context = koin) {
        FilmsTheme {
            BottomNavGraph(bottomNavHost = rememberNavController())
        }
    }
}