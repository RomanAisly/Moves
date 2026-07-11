package com.films.core.utils

import androidx.compose.ui.graphics.Color
import com.films.R
import com.films.ui.theme.green
import com.films.ui.theme.lightPink
import com.films.ui.theme.red
import com.films.ui.theme.unspecified

sealed interface CheckDataResult<out D, out E>{
    data class Success<D>(val data: D) : CheckDataResult<D, Nothing>
    data class Error(val error: AppError) : CheckDataResult<Nothing, AppError>
}

enum class AppError(
    val iconRes: Int,
    val messageRes: Int
) {
    NO_INTERNET(R.drawable.signal_disconnected, R.string.error_no_internet),
    TIMEOUT(R.drawable.hourglass_disabled, R.string.error_timeout),
    SERVER_ERROR(R.drawable.cloud_off, R.string.error_server),
    NOT_FOUND(R.drawable.search_off, R.string.error_not_found),
    UNAUTHORIZED(R.drawable.no_encryption, R.string.error_unauthorized),
    UNKNOWN(R.drawable.error, R.string.error_unknown)
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