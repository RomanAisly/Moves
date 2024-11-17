package com.moves.data.remote

import com.moves.data.remote.response.ResponseDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface FilmsAPI {
    @GET("3/movie/popular")
    suspend fun getFilmsByApi(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ): ResponseDTO
}