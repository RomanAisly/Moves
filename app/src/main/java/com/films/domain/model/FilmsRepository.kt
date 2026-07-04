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
}