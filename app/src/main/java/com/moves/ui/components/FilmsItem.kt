package com.moves.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.moves.R
import com.moves.domain.di.AppModule
import com.moves.domain.model.Films

@Composable
fun FilmsItem(modifier: Modifier = Modifier, films: Films) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        AsyncImage(
            model = AppModule.IMAGE_URL + films.poster_path,
            contentDescription = films.title,
            placeholder = painterResource(id = R.drawable.placeholder_image),
            error = painterResource(id = R.drawable.no_internet),
            contentScale = ContentScale.FillBounds,
            modifier = modifier
                .padding(8.dp)
                .clip(MaterialTheme.shapes.large)
                .clickable { }

        )

        Text(
            text = films.title,
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center,
            modifier = modifier.padding(bottom = 10.dp)
        )

        RatingBar(rating = films.vote_average / 2, modifier = modifier.padding(bottom = 2.dp))
    }
}