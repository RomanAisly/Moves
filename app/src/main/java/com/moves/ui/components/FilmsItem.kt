package com.moves.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.moves.R
import com.moves.data.remote.response.ResultDTO
import com.moves.domain.di.AppModule
import com.moves.domain.model.Films

@Composable
fun FilmsItem(modifier: Modifier = Modifier, films: Films) {
    Column(
        modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = AppModule.IMAGE_URL + films.poster_path,
            contentDescription = films.title,
            placeholder = painterResource(id = R.drawable.placeholder_image),
            error = painterResource(id = R.drawable.no_internet),
            modifier = modifier.size(120.dp)
        )

        Text(
            text = films.title,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurface
        )

//        RatingBar(rating = films.vote_average / 2)
    }
}