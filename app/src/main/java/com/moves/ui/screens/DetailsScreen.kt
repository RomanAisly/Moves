package com.moves.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.moves.R
import com.moves.data.remote.FilmsAPI
import com.moves.ui.components.RatingBar
import com.moves.ui.viewmodels.DetailsScreenViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun DetailsScreen(
    modifier: Modifier = Modifier,
    viewModel: DetailsScreenViewModel = koinViewModel()
) {

    val details by viewModel.details.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        AsyncImage(
            model = FilmsAPI.IMAGE_URL + details?.poster_path,
            contentDescription = "",
            placeholder = painterResource(id = R.drawable.placeholder_image),
            error = painterResource(id = R.drawable.no_internet),
            contentScale = ContentScale.FillBounds,
            modifier = modifier
                .padding(8.dp)
                .clip(MaterialTheme.shapes.large)
        )

        RatingBar(rating = 2.5, modifier = modifier)

        Text(
            text = details?.title ?: "",
            color = Color.White,
            fontSize = 18.sp
        )

//        details?.let { Text(text = it.overview) }
    }
}