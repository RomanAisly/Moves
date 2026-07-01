package com.moves.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.moves.R
import com.moves.data.remote.FilmsService
import com.moves.ui.components.BaseIconButton
import com.moves.ui.components.BaseText
import com.moves.ui.components.RatingBar
import com.moves.ui.viewmodels.DetailsScreenViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun DetailsScreen(
    filmId: Int,
    modifier: Modifier = Modifier,
    viewModel: DetailsScreenViewModel = koinViewModel()
) {

    val context = LocalContext.current
    val state by viewModel.state.collectAsState()


//    LaunchedEffect(viewModel.toast) {
//        viewModel.toast.collect { show ->
//            if (show) {
//                Toast.makeText(context, context.getString(R.string.toast), Toast.LENGTH_SHORT)
//                    .show()
//            }
//        }
//    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        AsyncImage(
            model = FilmsService.IMAGE_URL + (state.filmDetails?.poster_path ?: ""),
            contentDescription = state.filmDetails?.title,
            placeholder = painterResource(id = R.drawable.placeholder_image),
            error = painterResource(id = R.drawable.no_internet),
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .padding(8.dp)
                .clip(MaterialTheme.shapes.large)
        )

        RatingBar(rating = 2.5, modifier = modifier)

        BaseText(
            text = state.filmDetails?.title ?: "",
            textStyle = MaterialTheme.typography.titleLarge
        )

        BaseText(
            text = state.filmDetails?.release_date ?: ""
        )
        BaseText(
            text = state.filmDetails?.overview ?: "",
            modifier = Modifier.padding(horizontal = 8.dp)
        )
        Row(
            modifier = modifier
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