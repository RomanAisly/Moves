package com.films

import kotlinx.coroutines.flow.Flow

interface FilmsRepository {
    suspend fun getFilms(
        category: String,
        page: Int,
        language: String,
        forceFetch: Boolean
    ): Flow<CheckDataResult<List<Films>, AppError>>

    suspend fun getFilmById(id: Int): Flow<CheckDataResult<Films, AppError>>
    suspend fun getMovieTrailer(id: Int, language: String): Flow<CheckDataResult<String?, AppError>>
    suspend fun updateFavoriteStatus(id: Int, isFavorite: Boolean)
    fun getFavoriteFilms(): Flow<List<Films>>
    suspend fun updateWatchLaterStatus(id: Int, isWatchLater: Boolean)
    fun getWatchLaterFilms(): Flow<List<Films>>
    suspend fun getWatchProviders(
        filmId: Int,
        countryCode: String
    ): Flow<CheckDataResult<List<WatchProvider>, AppError>>
}