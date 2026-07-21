package com.films.remote

import kotlinx.serialization.Serializable

@Serializable
data class ResponseDTO(
    val page: Int,
    val results: List<ResultDTO>,
    val total_pages: Int,
    val total_results: Int
)

@Serializable
data class ResultDTO(
    val adult: Boolean,
    val backdrop_path: String?,
    val genre_ids: List<Int>,
    val id: Int,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String?,
    val release_date: String,
    val title: String,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Int
)

@Serializable
data class VideoResponseDTO(
    val id: Int,
    val results: List<VideoDTO>
)

@Serializable
data class VideoDTO(
    val key: String,
    val site: String,
    val type: String
)

@Serializable
data class WatchProvidersResponseDTO(
    val id: Int,
    val results: Map<String, CountryProviderDTO>
)

@Serializable
data class CountryProviderDTO(
    val link: String,
    val flatrate: List<ProviderDTO>? = null,
    val buy: List<ProviderDTO>? = null
)

@Serializable
data class ProviderDTO(
    val provider_name: String,
    val logo_path: String
)