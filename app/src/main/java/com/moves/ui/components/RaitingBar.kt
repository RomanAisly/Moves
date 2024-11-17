package com.moves.ui.components

import com.moves.R
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource

@Composable
fun RatingBar(
    modifier: Modifier = Modifier,
    starsModifier: Modifier = Modifier,
    rating: Double = 0.0,
    stars: Int = 5,
    starsColor: Color = Color.Yellow
) {
    val filledStars = kotlin.math.floor(rating).toInt()
    val outFilledStars = (stars - kotlin.math.ceil(rating)).toInt()
    val halfFilledStars = !(rating.rem(1).equals(0.0))

    Row(modifier = modifier) {
        repeat(filledStars) {
            Icon(
                modifier = starsModifier,
                imageVector = Icons.Rounded.Star,
                contentDescription = stringResource(R.string.cont_desc_rating_of_the_film),
                tint = starsColor
            )
        }
        if (halfFilledStars) {
            Icon(
                modifier = starsModifier,
                imageVector = Icons.Filled.Star,
                contentDescription = stringResource(R.string.cont_desc_rating_of_the_film),
                tint = starsColor
            )
        }
        repeat(outFilledStars) {
            Icon(
                modifier = starsModifier,
                imageVector = Icons.Default.Star,
                contentDescription = stringResource(R.string.cont_desc_rating_of_the_film),
                tint = starsColor
            )
        }
    }
}