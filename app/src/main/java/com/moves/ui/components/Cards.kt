package com.moves.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.moves.ui.theme.yellow

@Composable
fun FilmsItem(
    modifier: Modifier = Modifier,
    poster: Any?,
    title: String,
    genres: String,
    releaseDate: String,
    rating: Double,
    onFilmClick: () -> Unit
) {
    val imageUrl = "https://image.tmdb.org/t/p/w500"

    BaseCard(
        modifier = modifier.clickable { onFilmClick() },
        shape = MaterialTheme.shapes.large,
        border = BorderStroke(1.dp, yellow)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            CoilImage(
                model = imageUrl + poster,
                modifier = modifier
                    .fillMaxWidth()
                    .aspectRatio(2f / 3f)
            )
            BaseText(
                text = title,
                maxLines = 2,
                minLines = 2,
                textStyle = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center
            )
            BaseText(
                text = genres,
                maxLines = 1
            )
            BaseText(
                text = releaseDate
            )
            RatingBar(rating = rating / 2)
        }
    }
}