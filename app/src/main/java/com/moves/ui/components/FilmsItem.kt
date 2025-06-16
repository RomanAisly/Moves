package com.moves.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.moves.R
import com.moves.data.remote.KtorRequest
import com.moves.domain.model.Films
import com.moves.ui.theme.yellow

@Composable
fun FilmsItem(modifier: Modifier = Modifier, films: Films, onFilmClick: (id: Films) -> Unit) {

    Card(
        modifier = modifier
            .padding(4.dp),
        shape = MaterialTheme.shapes.large,
        border = BorderStroke(2.dp, yellow),

        ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center

        ) {
            AsyncImage(
                model = KtorRequest.IMAGE_URL + films.poster_path,
                contentDescription = films.title,
                placeholder = painterResource(id = R.drawable.placeholder_image),
                error = painterResource(id = R.drawable.no_internet),
                contentScale = ContentScale.FillBounds,
                modifier = modifier
                    .padding(2.dp)
                    .clickable { onFilmClick(films) }
            )

            SimpleText(
                text = films.title,
                textSize = 15.sp,
                modifier = modifier.padding(vertical = 6.dp)
            )

            SimpleText(
                text = films.release_date,
                textSize = 12.sp,
                modifier = modifier.padding(vertical = 6.dp)
            )

            RatingBar(rating = films.vote_average / 2, modifier = modifier.padding(bottom = 6.dp))
        }
    }

}