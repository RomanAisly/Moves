package com.moves.utils

sealed class ResultData<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T?) : ResultData<T>(data)
    class Error<T>(data: T? = null, message: String?) : ResultData<T>(data, message)
}