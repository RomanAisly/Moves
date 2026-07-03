package com.moves.ui.components

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import com.moves.R
import com.moves.core.di.previewModule
import com.moves.ui.theme.FilmsTheme
import org.koin.compose.KoinContext
import org.koin.dsl.koinApplication

enum class AppLanguage(
    val titleRes: Int,
    val localeCode: String
) {
    ENGLISH(
        R.string.en,
        "en"
    ),
    RUSSIAN(
        R.string.ru,
        "ru"
    )
}


enum class AppTheme {
    SYSTEM,
    LIGHT,
    DARK
}

enum class FilmCategory(
    val nameRes: Int,
    val category: String
) {
    POPULAR(R.string.film_category_popular, "popular"),
    NOW_PLAYING(R.string.film_category_now_playing, "now_playing"),
    TOP_RATED(R.string.film_category_top, "top_rated"),
    UPCOMING(R.string.film_category_upcoming, "upcoming")
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
        FilmsTheme {

        }
    }
}
