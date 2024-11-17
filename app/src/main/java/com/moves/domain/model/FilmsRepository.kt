package com.moves.domain.model

import com.moves.utils.ResultData
import kotlinx.coroutines.flow.Flow

interface FilmsRepository {
    suspend fun getFilms(page: Int, forceFetch: Boolean): Flow<ResultData<List<Films>>>
}