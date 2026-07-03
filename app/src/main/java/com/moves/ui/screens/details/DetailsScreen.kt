package com.moves.ui.screens.details

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.moves.R
import com.moves.data.mappers.toLocalizedGenresString
import com.moves.ui.components.BaseIconButton
import com.moves.ui.components.BaseText
import com.moves.ui.components.CoilImage
import com.moves.ui.components.LoadingScreen
import com.moves.ui.components.RatingBar
import org.koin.androidx.compose.koinViewModel

@Composable
fun DetailsScreen(
    viewModel: DetailsScreenViewModel = koinViewModel()
) {

    val context = LocalContext.current
    val state by viewModel.state.collectAsStateWithLifecycle()
    val imageUrl = "https://image.tmdb.org/t/p/w500"

    LaunchedEffect(viewModel.toast) {
        viewModel.toast.collect { show ->
            if (show) {
                Toast.makeText(context, R.string.toast, Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    if (state.filmDetails == null) {
        LoadingScreen()
        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        CoilImage(
            model = imageUrl + state.filmDetails?.poster_path,
            placeholder = R.drawable.placeholder_image,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(3f / 2f)
        )

        RatingBar(rating = state.filmDetails?.vote_average?.div(2) ?: 0.0)

        BaseText(
            text = state.filmDetails?.title ?: "",
            textStyle = MaterialTheme.typography.titleLarge
        )

        BaseText(
            text = state.filmDetails?.genreIds?.toLocalizedGenresString() ?: ""
        )

        BaseText(
            text = state.filmDetails?.release_date ?: ""
        )
        BaseText(
            text = state.filmDetails?.overview ?: "",
            modifier = Modifier.padding(horizontal = 8.dp)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            BaseIconButton(
                iconId = if (!state.isFavorite) {
                    R.drawable.favorite
                } else {
                    R.drawable.favorite_fill
                }, onClick = {})
        }
    }

}