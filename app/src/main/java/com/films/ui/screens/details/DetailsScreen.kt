package com.films.ui.screens.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.films.R
import com.films.data.mappers.toLocalizedGenresString
import com.films.data.remote.FilmsService
import com.films.ui.components.BaseIconButton
import com.films.ui.components.BaseText
import com.films.ui.components.CoilImage
import com.films.ui.components.CustomSnaKBar
import com.films.ui.components.LoadingScreen
import com.films.ui.components.RatingBar
import org.koin.androidx.compose.koinViewModel

@Composable
fun DetailsScreen(
    viewModel: DetailsScreenViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    if (state.filmDetails == null) {
        LoadingScreen()
        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        CoilImage(
            model = FilmsService.IMAGE_URL + state.filmDetails?.poster_path,
            placeholder = R.drawable.placeholder_image,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp))
                .aspectRatio(5f / 6f)
        )

        RatingBar(rating = state.filmDetails?.vote_average?.div(2) ?: 0.0)

        BaseText(
            text = state.filmDetails?.title ?: "",
            textStyle = MaterialTheme.typography.headlineLarge,
            modifier = Modifier
                .padding(horizontal = 16.dp)
        )

        BaseText(
            text = state.filmDetails?.genreIds?.toLocalizedGenresString() ?: "",
            textStyle = MaterialTheme.typography.headlineMedium,
            modifier = Modifier
                .padding(horizontal = 16.dp)
        )

        BaseText(
            text = state.filmDetails?.release_date ?: "",
            textStyle = MaterialTheme.typography.headlineSmall
        )
        BaseText(
            text = state.filmDetails?.overview ?: "",
            textStyle = MaterialTheme.typography.bodyLarge,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(top = 8.dp)
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

        CustomSnaKBar(snackBarFlow = viewModel.snack)
    }

}