package com.films.domain.model

import com.films.core.utils.AppError
import com.films.core.utils.CheckDataResult
import kotlinx.coroutines.flow.Flow

interface FilmsRepository {
    suspend fun getFilms(
        category: String,
        page: Int,
        language: String,
        forceFetch: Boolean
    ): Flow<CheckDataResult<List<Films>, AppError>>

    suspend fun getFilmById(id: Int): Flow<CheckDataResult<Films, AppError>>

    suspend fun updateFavoriteStatus(id: Int, isFavorite: Boolean)
    fun getFavoriteFilms(): Flow<List<Films>>
    suspend fun updateWatchLaterStatus(id: Int, isWatchLater: Boolean)
    fun getWatchLaterFilms(): Flow<List<Films>>
}