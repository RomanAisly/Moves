package com.moves.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.navigation.compose.rememberNavController
import com.moves.R
import com.moves.domain.di.previewModule
import com.moves.ui.navigation.BottomNavGraph
import com.moves.ui.theme.FilmsTheme
import com.moves.ui.theme.yellow
import org.koin.compose.KoinApplication
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

enum class SnackBarType(
    val messageRes: Int,
    val iconRes: Int
) {

}

enum class FilmCategory(
    val nameRes: Int,
    val category: String,
    val colorRes: Color = yellow
) {
    POPULAR(R.string.film_category_popular, "popular"),
    NOW_PLAYING(R.string.film_category_now_playing, "now_playing"),
    TOP_RATED(R.string.film_category_top, "top_rated"),
    UPCOMING(R.string.film_category_upcoming, "upcoming")
}



@Composable
@PreviewLightDark
@Preview(showBackground = true, showSystemUi = true)
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
