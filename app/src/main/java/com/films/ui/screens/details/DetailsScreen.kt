package com.films.ui.screens.details

import android.content.Context
import android.content.Intent
import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.films.R
import com.films.data.mappers.toLocalizedGenresString
import com.films.data.remote.FilmsService
import com.films.ui.components.BaseCard
import com.films.ui.components.BaseIcon
import com.films.ui.components.BaseIconButton
import com.films.ui.components.BaseScreen
import com.films.ui.components.BaseText
import com.films.ui.components.CoilImage
import com.films.ui.components.LoadingScreen
import com.films.ui.components.RatingBar
import com.films.ui.components.SnackBarFlow
import com.films.ui.theme.AppTheme
import com.films.ui.theme.LocalSetLanguage
import com.films.ui.theme.black
import com.films.ui.theme.green
import com.films.ui.theme.lightPink
import com.films.ui.theme.red
import com.films.ui.theme.royalBlue
import com.films.ui.theme.transparent
import com.films.ui.theme.white
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
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
    val context = LocalContext.current
    val currentLanguage = LocalSetLanguage.current

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
                iconTint = if (state.isWatchLater) red else green,
                onClick = { viewModel.addToWatchLater(!state.isWatchLater) }
            )
            ActionIconButton(
                iconId = R.drawable.share,
                iconTint = royalBlue,
                onClick = { shareMovie(context, film.title, film.id, currentLanguage.localeCode) }
            )
        }

        if (state.trailerKey != null) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                BaseText(
                    text = stringResource(R.string.trailer),
                    textStyle = MaterialTheme.typography.headlineMedium
                )
                YouTubeTrailerPlayer(youtubeKey = state.trailerKey!!)
            }
        }

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
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

private fun shareMovie(context: Context, filmTitle: String, filmId: Int, languageCode: String) {
    val tmdbUrl = "https://www.themoviedb.org/movie/$filmId?language=$languageCode"

    val chooserTitle = context.getString(R.string.share_title)
    val sendIntent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(
            Intent.EXTRA_TITLE,
            filmTitle
        )
        putExtra(Intent.EXTRA_TEXT, tmdbUrl)
    }
    val chooserIntent = Intent.createChooser(sendIntent, chooserTitle)
    chooserIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
    context.startActivity(chooserIntent)
}

@Composable
private fun YouTubeTrailerPlayer(
    youtubeKey: String,
    modifier: Modifier = Modifier
) {
    var isPlayerReady by remember { mutableStateOf(false) }

    val borderColor by animateColorAsState(
        targetValue = if (isPlayerReady) transparent else AppTheme.colors.text.copy(alpha = 0.5f),
        animationSpec = tween(durationMillis = 500)
    )

    BaseCard(
        border = BorderStroke(4.dp, borderColor),
        elevation = 6.dp
    ) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .aspectRatio(16f / 9f)
                .clip(MaterialTheme.shapes.medium)
                .clickable { isPlayerReady = true }
        ) {
            Crossfade(targetState = isPlayerReady) { ready ->
                if (!ready) {
                    CoilImage(
                        model = "https://img.youtube.com/vi/$youtubeKey/hqdefault.jpg",
                        placeholder = R.drawable.placeholder_image,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                    Box(
                        modifier = Modifier
                            .size(62.dp)
                            .align(Alignment.Center)
                            .background(black.copy(alpha = 0.5f), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        BaseIcon(
                            iconId = R.drawable.play_circle,
                            iconTint = white,
                            modifier = Modifier.size(34.dp)
                        )
                    }
                } else {
                    val lifecycleOwner = LocalLifecycleOwner.current
                    AndroidView(
                        modifier = Modifier.fillMaxSize(),
                        factory = { context ->
                            YouTubePlayerView(context).apply {
                                lifecycleOwner.lifecycle.addObserver(this)

                                enableAutomaticInitialization = false
                                val iFramePlayerOptions = IFramePlayerOptions.Builder(context)
                                    .controls(1)
                                    .rel(0)
                                    .ivLoadPolicy(3)
                                    .ccLoadPolicy(0)
                                    .build()
                                initialize(object : AbstractYouTubePlayerListener() {
                                    override fun onReady(youTubePlayer: YouTubePlayer) {
                                        youTubePlayer.loadVideo(youtubeKey, 0f)
                                    }
                                }, iFramePlayerOptions)
                            }
                        }
                    )
                }
            }
        }
    }
}