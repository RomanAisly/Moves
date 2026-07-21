package com.films.mappers

import com.films.Films
import com.films.local.FilmsEntity
import com.films.remote.ResultDTO

fun ResultDTO.toFilmsEntity(
    category: String,
    language: String,
    isFavorite: Boolean = false,
    isWatchLater: Boolean = false
): FilmsEntity {
    return FilmsEntity(
        adult = adult,
        backdrop_path = backdrop_path,
        genre_ids = genre_ids.joinToString(","),
        id = id,
        original_language = original_language,
        original_title = original_title,
        overview = overview,
        popularity = popularity,
        poster_path = poster_path,
        release_date = release_date,
        title = title,
        video = video,
        vote_average = vote_average,
        vote_count = vote_count,
        category = category,
        language = language,
        isFavorite = isFavorite,
        isWatchLater = isWatchLater
    )
}

fun FilmsEntity.toLocalFilms(category: String): Films {
    return Films(
        adult = adult,
        backdrop_path = backdrop_path,
        genreIds = if (genre_ids.isBlank()) {
            emptyList()
        } else {
            genre_ids.split(",").map { it.toInt() }
        },
        id = id,
        original_language = original_language,
        original_title = original_title,
        overview = overview,
        popularity = popularity,
        poster_path = poster_path,
        release_date = release_date,
        title = title,
        video = video,
        vote_average = vote_average,
        vote_count = vote_count,
        category = category,
        isFavorite = isFavorite,
        isWatchLater = isWatchLater
    )
}