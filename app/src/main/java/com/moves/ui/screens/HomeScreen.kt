package com.moves.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.moves.R
import com.moves.ui.components.FilmsItem
import com.moves.ui.components.LoadingScreen
import com.moves.ui.viewmodels.HomeScreenViewModel

@Composable
fun HomeScreen(modifier: Modifier = Modifier, viewModel: HomeScreenViewModel = hiltViewModel()) {

    val context = LocalContext.current
    val allFilms by viewModel.allFilms.collectAsState()

    LaunchedEffect(viewModel.toast) {
        viewModel.toast.collect { show ->
            if (show) {
                Toast.makeText(context, context.getString(R.string.toast), Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    if (allFilms.isEmpty()) {
        LoadingScreen()
    } else {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = modifier
                .fillMaxSize()
                .padding(vertical = 6.dp)
        ) {
            items(allFilms.size) { index ->
                FilmsItem(films = allFilms[index], modifier = modifier)
            }
        }
    }
}