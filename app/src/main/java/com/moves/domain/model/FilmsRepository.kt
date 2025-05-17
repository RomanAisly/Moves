package com.moves.domain.model

import com.moves.utils.HttpStatus
import com.moves.utils.CheckDataResult
import kotlinx.coroutines.flow.Flow

interface FilmsRepository {
    suspend fun getFilms(
        category: String,
        page: Int,
        forceFetch: Boolean
    ): Flow<CheckDataResult<List<Films>, HttpStatus>>

    suspend fun getFilmById(id: Int): Flow<CheckDataResult<Films, HttpStatus>>
}