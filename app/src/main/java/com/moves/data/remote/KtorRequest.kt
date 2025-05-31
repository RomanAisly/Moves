package com.moves.data.remote

import com.moves.BuildConfig
import com.moves.data.remote.response.ResponseDTO
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class KtorRequest(private val httpClient: HttpClient) {
    suspend fun getFilms(
        category: String,
        apiKey: String = BuildConfig.API_KEY,
        page: Int
    ): ResponseDTO {
        val response = httpClient.get("movie/$category") {
            parameter("category", category)
            parameter("api_key", apiKey)
            parameter("page", page)
        }
        return if (response.status.value in 200..299) {
            response.body()
        } else {
            throw Exception("Error is: ${response.status.value}")
        }
    }

    companion object {
        const val BASE_URL = "https://api.themoviedb.org/3/"
        const val IMAGE_URL = "https://image.tmdb.org/t/p/w500"
    }
}