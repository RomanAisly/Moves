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

    companion object{
        const val BASE_URL = "https://api.themoviedb.org/3/"
        const val IMAGE_URL = "https://image.tmdb.org/t/p/w500"
    }
}