package com.films.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.films.AppError
import com.films.AppLanguage
import com.films.AppTheme
import com.films.ui.R

fun AppError.getIconRes(): Int = when (this) {
    AppError.NO_INTERNET -> R.drawable.signal_disconnected
    AppError.TIMEOUT -> R.drawable.hourglass_disabled
    AppError.SERVER_ERROR -> R.drawable.cloud_off
    AppError.NOT_FOUND -> R.drawable.search_off
    AppError.UNAUTHORIZED -> R.drawable.no_encryption
    AppError.UNKNOWN -> R.drawable.error
}

fun AppError.getMessageRes(): Int = when (this) {
    AppError.NO_INTERNET -> R.string.error_no_internet
    AppError.TIMEOUT -> R.string.error_timeout
    AppError.SERVER_ERROR -> R.string.error_server
    AppError.NOT_FOUND -> R.string.error_not_found
    AppError.UNAUTHORIZED -> R.string.error_unauthorized
    AppError.UNKNOWN -> R.string.error_unknown
}

fun AppTheme.getTitleRes(): Int = when (this) {
    AppTheme.SYSTEM -> R.string.theme_system
    AppTheme.DARK -> R.string.theme_dark
    AppTheme.LIGHT -> R.string.theme_light
}

fun AppTheme.getIconRes(): Int = when (this) {
    AppTheme.SYSTEM -> R.drawable.theme_system
    AppTheme.DARK -> R.drawable.theme_dark
    AppTheme.LIGHT -> R.drawable.theme_light
}

fun AppLanguage.getTitleRes(): Int = when (this) {
    AppLanguage.ENGLISH -> R.string.en
    AppLanguage.RUSSIAN -> R.string.ru
    AppLanguage.CHINESE -> R.string.zh
    AppLanguage.SPANISH -> R.string.es
    AppLanguage.FRENCH -> R.string.fr
    AppLanguage.GERMAN -> R.string.de
    AppLanguage.UKRAINIAN -> R.string.uk
}

val tmdbGenresMap = mapOf(
    28 to R.string.genre_action,
    12 to R.string.genre_adventure,
    16 to R.string.genre_animation,
    35 to R.string.genre_comedy,
    80 to R.string.genre_crime,
    99 to R.string.genre_documentary,
    18 to R.string.genre_drama,
    10751 to R.string.genre_family,
    14 to R.string.genre_fantasy,
    36 to R.string.genre_history,
    27 to R.string.genre_horror,
    10402 to R.string.genre_music,
    9648 to R.string.genre_mystery,
    10749 to R.string.genre_romance,
    878 to R.string.genre_science_fiction,
    10770 to R.string.genre_tv_movie,
    53 to R.string.genre_thriller,
    10752 to R.string.genre_war,
    37 to R.string.genre_western
)

@Composable
fun List<Int>.toLocalizedGenresString(): String {
    return this.mapNotNull { id -> tmdbGenresMap[id] }
        .map { stringResId -> stringResource(id = stringResId) }
        .joinToString(", ")
}