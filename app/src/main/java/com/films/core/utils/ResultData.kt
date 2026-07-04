package com.films.core.utils

import com.films.R

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
