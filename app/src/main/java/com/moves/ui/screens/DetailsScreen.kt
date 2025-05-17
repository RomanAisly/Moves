package com.moves.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.WatchLater
import androidx.compose.material.icons.outlined.WatchLater
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.moves.R
import com.moves.data.remote.FilmsAPI
import com.moves.ui.components.CustomIcon
import com.moves.ui.components.RatingBar
import com.moves.ui.components.SimpleText
import com.moves.ui.events.DetailScreenEvents
import com.moves.ui.theme.red
import com.moves.ui.theme.yellow
import com.moves.ui.viewmodels.DetailsScreenViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun DetailsScreen(
    filmId: Int,
    modifier: Modifier = Modifier,
    viewModel: DetailsScreenViewModel = koinViewModel()
) {

    val context = LocalContext.current
    val state by viewModel.state.collectAsState()
    viewModel.onEvent(DetailScreenEvents.GetFilmDetails(filmId))



    LaunchedEffect(viewModel.toast) {
        viewModel.toast.collect { show ->
            if (show) {
                Toast.makeText(context, context.getString(R.string.toast), Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        AsyncImage(
            model = FilmsAPI.IMAGE_URL + (state.filmDetails?.poster_path ?: ""),
            contentDescription = state.filmDetails?.title,
            placeholder = painterResource(id = R.drawable.placeholder_image),
            error = painterResource(id = R.drawable.no_internet),
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .padding(8.dp)
                .clip(MaterialTheme.shapes.large)
        )

        RatingBar(rating = 2.5, modifier = modifier)

        SimpleText(
            text = state.filmDetails?.title ?: "",
            textSize = 24.sp
        )

        SimpleText(
            text = state.filmDetails?.release_date ?: "",
            textSize = 16.sp
        )
        SimpleText(
            text = state.filmDetails?.overview ?: "",
            textSize = 16.sp,
            modifier = Modifier.padding(horizontal = 8.dp)
        )
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            IconButton(onClick = { viewModel.onEvent(DetailScreenEvents.UpdateFavorite(state.isFavorite)) }) {
                if (!state.isFavorite) {
                    CustomIcon(
                        icon = Icons.Rounded.FavoriteBorder,
                        contDesc = ""
                    )

                } else {
                    CustomIcon(
                        icon = Icons.Filled.Favorite,
                        contDesc = "",
                        tint = red
                    )
                }
            }
            IconButton(onClick = { viewModel.onEvent(DetailScreenEvents.UpdateWatchLater(state.isWatchLater)) }) {
                if (!state.isWatchLater) {
                    CustomIcon(
                        icon = Icons.Outlined.WatchLater,
                        contDesc = ""
                    )
                } else {
                    CustomIcon(
                        icon = Icons.Filled.WatchLater,
                        contDesc = "",
                        tint = yellow
                    )
                }
            }
        }
    }

}