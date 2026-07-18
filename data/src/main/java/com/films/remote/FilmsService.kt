package com.films.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class FilmsService(private val httpClient: HttpClient) {
    suspend fun getFilms(
        category: String,
        page: Int,
        language: String
    ): ResponseDTO {
        val response = httpClient.get("movie/$category") {
            parameter("page", page)
            parameter("language", language)
        }
        return if (response.status.value in 200..299) {
            response.body()
        } else {
            throw Exception("Error is: ${response.status.value}")
        }
    }

    suspend fun getMovieVideos(id: Int, language: String): VideoResponseDTO {
        val response = httpClient.get("movie/$id/videos") {
            parameter("language", language)
        }
        return if (response.status.value in 200..299) {
            response.body()
        } else {
            throw Exception("Error is: ${response.status.value}")
        }
    }

    suspend fun getWatchProviders(filmId: Int): WatchProvidersResponseDTO {
        val response = httpClient.get("movie/$filmId/watch/providers")
        return if (response.status.value in 200..299) {
            response.body()
        } else {
            throw Exception("Error is: ${response.status.value}")
        }
    }
}