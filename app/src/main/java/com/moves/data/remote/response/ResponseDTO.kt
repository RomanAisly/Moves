package com.moves.data.remote.response

import kotlinx.serialization.Serializable

@Serializable
data class ResponseDTO(
    val page: Int,
    val results: List<ResultDTO>,
    val total_pages: Int,
    val total_results: Int
)
