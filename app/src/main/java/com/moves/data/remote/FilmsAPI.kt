package com.moves.data.remote

import com.moves.BuildConfig
import com.moves.data.remote.response.ResponseDTO
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface FilmsAPI {
    @GET("movie/{category}")
    suspend fun getFilmsByApi(
        @Path("category") category: String,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("page") page: Int
    ): ResponseDTO
}