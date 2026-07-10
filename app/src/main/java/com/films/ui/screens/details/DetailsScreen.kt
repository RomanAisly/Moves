package com.films.ui.screens.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.films.R
import com.films.data.mappers.toLocalizedGenresString
import com.films.data.remote.FilmsService
import com.films.ui.components.BaseCard
import com.films.ui.components.BaseIconButton
import com.films.ui.components.BaseScreen
import com.films.ui.components.BaseText
import com.films.ui.components.CoilImage
import com.films.ui.components.LoadingScreen
import com.films.ui.components.RatingBar
import com.films.ui.components.SnackBarFlow
import com.films.ui.theme.AppTheme
import com.films.ui.theme.lightPink
import com.films.ui.theme.lime
import com.films.ui.theme.red
import com.films.ui.theme.royalBlue
import org.koin.androidx.compose.koinViewModel

@Composable
fun DetailsScreen(
    viewModel: DetailsViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    if (state.filmDetails == null) {
        LoadingScreen()
        return
    }
    val film = state.filmDetails!!

    BaseScreen(
        modifier = Modifier.verticalScroll(rememberScrollState()),
        useStatusBarsPadding = false,
        horizontal = Alignment.CenterHorizontally,
        vertical = Arrangement.spacedBy(14.dp),
        overlayContent = {
            SnackBarFlow(
                snackBarFlow = viewModel.snack,
                iconRes = { it.iconRes },
                messageRes = { it.messageRes },
                modifier = Modifier
                    .align(Alignment.BottomCenter)

            )
            SnackBarFlow(
                snackBarFlow = viewModel.successSnack,
                iconRes = { it.iconRes },
                messageRes = { it.messageRes },
                iconTint = { it.iconTint },
                modifier = Modifier
                    .align(Alignment.BottomCenter)

            )
        }
    ) {
        BaseCard(
            modifier = Modifier.fillMaxWidth(),
            elevation = 8.dp,
            shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp)
        ) {
            CoilImage(
                model = FilmsService.IMAGE_URL + film.poster_path,
                placeholder = R.drawable.placeholder_image,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp))
                    .aspectRatio(5f / 6f)
            )
        }

        RatingBar(rating = film.vote_average / 2)

        BaseText(
            text = film.title,
            textStyle = MaterialTheme.typography.headlineLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        BaseText(
            text = film.genreIds.toLocalizedGenresString(),
            textStyle = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        BaseText(
            text = film.release_date,
            textStyle = MaterialTheme.typography.headlineSmall
        )

        BaseText(
            text = film.overview,
            textStyle = MaterialTheme.typography.bodyLarge,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(top = 8.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            ActionIconButton(
                iconId = if (state.isFavorite) R.drawable.favorite_fill else R.drawable.favorite,
                iconTint = lightPink,
                onClick = { viewModel.addToFavorite(!state.isFavorite) }
            )
            ActionIconButton(
                iconId = if (state.isWatchLater) R.drawable.time_delete else R.drawable.time_add,
                iconTint = if (state.isWatchLater) red else lime,
                onClick = { viewModel.addToWatchLater(!state.isWatchLater) }
            )
            ActionIconButton(
                iconId = R.drawable.share,
                iconTint = royalBlue,
                onClick = { /* Share logic */ }
            )
        }

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(68.dp)
        )
    }
}

@Composable
private fun ActionIconButton(
    iconId: Int,
    iconTint: Color,
    onClick: () -> Unit
) {
    BaseIconButton(
        size = 48.dp,
        modifier = Modifier
            .shadow(
                elevation = 2.dp,
                shape = CircleShape,
                spotColor = AppTheme.colors.text,
                ambientColor = AppTheme.colors.text
            )
            .background(AppTheme.colors.iconBack, shape = CircleShape),
        iconTint = iconTint,
        iconId = iconId,
        onClick = onClick
    )
}