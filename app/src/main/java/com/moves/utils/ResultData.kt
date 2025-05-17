package com.moves.utils

sealed interface CheckDataResult<out D, out E>{
    data class Success<D>(val data: D) : CheckDataResult<D, Nothing>
    data class Error<E>(val error: E) : CheckDataResult<Nothing, E>
}

enum class HttpStatus(val code: Int, val message: String){
    OK(200, "OK"),
    CREATED(201, "CREATED"),
    BAD_REQUEST(400, "BAD_REQUEST"),
    UNAUTHORIZED(401, "UNAUTHORIZED"),
    NOT_FOUND(404, "NOT_FOUND"),
    INTERNAL_SERVER_ERROR(500, "INTERNAL_SERVER_ERROR");

    fun toResponseString() = "$code $message"
}