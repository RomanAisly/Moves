package com.moves.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.moves.R

@Composable
fun BaseIcon(
    iconId: Int,
    modifier: Modifier = Modifier,
    iconTint: Color = Color.Unspecified
) {
    Icon(
        modifier = modifier,
        painter = painterResource(iconId),
        contentDescription = null,
        tint = iconTint
    )
}

@Composable
fun BaseIconButton(
    iconId: Int,
    modifier: Modifier = Modifier,
    size: Dp = 24.dp,
    iconTint: Color = Color.Unspecified,
    onClick: () -> Unit
) {
    IconButton(
        modifier = modifier.size(size),
        onClick = onClick
    ) {
        Icon(
            painter = painterResource(iconId),
            contentDescription = null,
            tint = iconTint
        )
    }
}

@Composable
fun CoilImage(
    model: Any?,
    modifier: Modifier = Modifier,
    placeholder: Int? = null,
    contentScale: ContentScale = ContentScale.Crop
) {
    AsyncImage(
        model = model,
        contentDescription = null,
        modifier = modifier,
        placeholder = placeholder?.let { painterResource(id = it) },
        contentScale = contentScale
    )
}

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
    val halfFilledStars = rating.rem(1) != 0.0

    Row(modifier = modifier) {
        repeat(filledStars) {
            BaseIcon(
                iconId = R.drawable.star_fill,
                modifier = starsModifier,
                iconTint = starsColor
            )
        }
        if (halfFilledStars) {
            BaseIcon(
                iconId = R.drawable.star_half,
                modifier = starsModifier,
                iconTint = starsColor
            )
        }
        repeat(outFilledStars) {
            BaseIcon(
                iconId = R.drawable.star_outline,
                modifier = starsModifier,
                iconTint = starsColor
            )
        }
    }
}