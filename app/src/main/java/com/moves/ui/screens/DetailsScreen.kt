package com.moves.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun DetailsScreen(
    modifier: Modifier = Modifier,
) {

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
//        AsyncImage(
//            model = AppModule.IMAGE_URL + details?.poster_path,
//            contentDescription = details?.title,
//            placeholder = painterResource(id = R.drawable.placeholder_image),
//            error = painterResource(id = R.drawable.no_internet),
//            contentScale = ContentScale.FillBounds,
//            modifier = modifier
//                .padding(8.dp)
//                .clip(MaterialTheme.shapes.large)
//        )
//
//        RatingBar(rating = details?.vote_average?.div(2) ?: 0.5)

//        details?.title?.let { Text(text = it) }
//
//        details?.let { Text(text = it.overview) }
    }
}