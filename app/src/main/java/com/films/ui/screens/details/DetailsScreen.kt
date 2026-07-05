package com.films.ui.screens.details

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.films.R
import com.films.core.di.previewModule
import com.films.data.mappers.toLocalizedGenresString
import com.films.data.remote.FilmsService
import com.films.ui.components.BaseIconButton
import com.films.ui.components.BaseScreen
import com.films.ui.components.BaseText
import com.films.ui.components.CoilImage
import com.films.ui.components.RatingBar
import com.films.ui.theme.FilmsTheme
import com.films.ui.theme.gray
import com.films.ui.theme.lightPink
import com.films.ui.theme.lime
import com.films.ui.theme.red
import com.films.ui.theme.royalBlue
import com.films.ui.theme.white
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.KoinContext
import org.koin.dsl.koinApplication

@Composable
fun DetailsScreen(
    viewModel: DetailsViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

//    if (state.filmDetails == null) {
//        LoadingScreen()
//        return
//    }

    BaseScreen(
        modifier = Modifier.verticalScroll(rememberScrollState()),
        useStatusBarsPadding = false,
        horizontal = Alignment.CenterHorizontally,
        vertical = Arrangement.spacedBy(12.dp)
    ) {
        CoilImage(
            model = FilmsService.IMAGE_URL + state.filmDetails?.poster_path,
            placeholder = R.drawable.placeholder_image,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp))
                .aspectRatio(5f / 6f)
        )

        RatingBar(rating = state.filmDetails?.vote_average?.div(2) ?: 0.0)

        BaseText(
            text = state.filmDetails?.title ?: "",
            textStyle = MaterialTheme.typography.headlineLarge,
            modifier = Modifier
                .padding(horizontal = 16.dp)
        )

        BaseText(
            text = state.filmDetails?.genreIds?.toLocalizedGenresString() ?: "",
            textStyle = MaterialTheme.typography.headlineMedium,
            modifier = Modifier
                .padding(horizontal = 16.dp)
        )

        BaseText(
            text = state.filmDetails?.release_date ?: "",
            textStyle = MaterialTheme.typography.headlineSmall
        )
        BaseText(
            text = state.filmDetails?.overview ?: "",
            textStyle = MaterialTheme.typography.bodyLarge,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(top = 8.dp)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            BaseIconButton(
                size = 48.dp,
                modifier = Modifier
                    .background(
                        white,
                        shape = CircleShape
                    )
                    .border(2.dp, MaterialTheme.colorScheme.outline, CircleShape),
                iconTint = if (!state.isFavorite) gray else lightPink,
                iconId = if (!state.isFavorite) {
                    R.drawable.favorite
                } else {
                    R.drawable.favorite_fill
                }, onClick = { viewModel.addToFavorite(!state.isFavorite) })

            BaseIconButton(
                size = 48.dp,
                modifier = Modifier
                    .background(
                        white,
                        shape = CircleShape
                    )
                    .border(2.dp, MaterialTheme.colorScheme.outline, CircleShape),
                iconTint = if (!state.isWatchLater) lime else red,
                iconId = if (!state.isWatchLater) {
                    R.drawable.time_add
                } else {
                    R.drawable.time_delete
                }, onClick = { viewModel.addToWatchLater(!state.isWatchLater) })

            BaseIconButton(
                size = 48.dp,
                modifier = Modifier
                    .background(
                        white,
                        shape = CircleShape
                    )
                    .border(2.dp, MaterialTheme.colorScheme.outline, CircleShape),
                iconTint = royalBlue,
                iconId = R.drawable.share,
                onClick = { })
        }

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        )
//        CustomSnaKBar(snackBarFlow = viewModel.snack)
    }

}

@Composable
@Preview(name = "Light Mode", showBackground = true, showSystemUi = true)
@Preview(
    name = "Dark Mode",
    showBackground = true,
    showSystemUi = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
private fun Preview() {
    val koin = remember {
        koinApplication {
            modules(previewModule)
        }.koin
    }
    KoinContext(context = koin) {
        FilmsTheme(onThemeChange = {}) {
            DetailsScreen()
        }
    }
}
