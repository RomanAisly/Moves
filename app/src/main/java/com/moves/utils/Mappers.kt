package com.moves.utils

import com.moves.data.local.FilmsEntity
import com.moves.data.remote.response.ResultDTO
import com.moves.domain.model.Films

fun ResultDTO.toFilmsEntity(): FilmsEntity {
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
        vote_count = vote_count
    )
}

fun FilmsEntity.toLocalFilms(): Films {
    return Films(
        adult = adult,
        backdrop_path = backdrop_path,
        genre_ids = genre_ids.split(",").map { it.toInt() },
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
        vote_count = vote_count
    )
}