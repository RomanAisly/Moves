package com.films.data.mappers

import com.films.data.local.FilmsEntity
import com.films.data.remote.ResultDTO
import com.films.domain.model.Films

fun ResultDTO.toFilmsEntity(category: String, language: String): FilmsEntity {
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
        isFavorite = false,
        isWatchLater = false
    )
}

fun FilmsEntity.toLocalFilms(category: String): Films {

    return Films(
        adult = adult,
        backdrop_path = backdrop_path,
        genreIds = genre_ids.split(",").map { it.toInt() },
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