package com.moves.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.moves.R
import com.moves.data.remote.FilmsService
import com.moves.domain.model.Films
import com.moves.ui.theme.yellow

@Composable
fun FilmsItem(
    modifier: Modifier = Modifier,
    films: Films,
    onFilmClick: () -> Unit
) {

    BaseCard(
        modifier = modifier,
        shape = MaterialTheme.shapes.large,
        border = BorderStroke(2.dp, yellow)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CoilImage(
                model = FilmsService.IMAGE_URL + films.poster_path,
                contentScale = ContentScale.FillBounds,
                modifier = modifier
                    .fillMaxSize()
                    .clickable { onFilmClick() }
            )
            BaseText(
                text = films.title,
                modifier = modifier.padding(vertical = 6.dp)
            )
            BaseText(
                text = films.release_date
            )
            RatingBar(rating = films.vote_average / 2, modifier = modifier.padding(vertical = 6.dp))
        }
    }

}