package com.moves.domain.model

import com.moves.utils.ResultData
import kotlinx.coroutines.flow.Flow

interface FilmsRepository {
    suspend fun getFilms(
        category: String,
        page: Int,
        forceFetch: Boolean
    ): Flow<ResultData<List<Films>>>

    suspend fun getFilmById(id: Int): Flow<ResultData<Films>>
}