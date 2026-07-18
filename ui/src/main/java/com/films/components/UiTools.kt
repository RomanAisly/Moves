package com.films.components

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.films.theme.FilmsTheme
import com.films.theme.green
import com.films.theme.lightPink
import com.films.theme.red
import com.films.theme.unspecified
import com.films.ui.R
import org.koin.compose.KoinContext
import org.koin.dsl.koinApplication

enum class FilmCategory(
    val nameRes: Int,
    val category: String
) {
    POPULAR(R.string.film_category_popular, "popular"),
    NOW_PLAYING(R.string.film_category_now_playing, "now_playing"),
    TOP_RATED(R.string.film_category_top, "top_rated"),
    UPCOMING(R.string.film_category_upcoming, "upcoming")
}

enum class AppSuccess(
    val iconRes: Int,
    val messageRes: Int,
    val iconTint: Color = unspecified
) {
    ADDED_TO_FAVORITES(R.drawable.favorite_fill, R.string.added_to_favorites, lightPink),
    REMOVED_FROM_FAVORITES(R.drawable.favorite, R.string.removed_from_favorites, lightPink),
    ADDED_TO_WATCH_LATER(R.drawable.time_add, R.string.added_to_watch_later, green),
    REMOVED_FROM_WATCH_LATER(R.drawable.time_delete, R.string.removed_from_watch_later, red)
}

const val IMAGE_URL = "https://image.tmdb.org/t/p/w500"

@Composable
@Preview(
    name = "Light Mode",
    showBackground = true,
    showSystemUi = true
)
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

        }
    }
}
