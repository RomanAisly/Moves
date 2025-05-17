package com.moves.data.remote

import com.moves.BuildConfig
import com.moves.data.remote.response.ResponseDTO
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.ANDROID
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import okhttp3.logging.HttpLoggingInterceptor

class KtorRequest(private val httpClient: HttpClient) {
    suspend fun getFilms(): ResponseDTO {
        val response = httpClient.get(BASE_URL) {
            parameter("api_key", BuildConfig.API_KEY)
            parameter("language", "en-US")
            parameter("page", 1)
            parameter("region", "RU")
        }
        if (response.status.value in 200..299) {
            return response.body()
        } else {
            throw Exception("Error")
        }
    }

    companion object {
        const val BASE_URL = "https://api.themoviedb.org/3/"
        const val IMAGE_URL = "https://image.tmdb.org/t/p/w500"
    }
}

object HttpClientFactory {
    fun create(engine: HttpClientEngine): HttpClient {
        return HttpClient(engine) {

            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                }
                )
            }

            install(HttpTimeout) {
                socketTimeoutMillis = 10_000L
                requestTimeoutMillis = 10_000L
            }

            install(Logging) {
                level = LogLevel.ALL
                logger = Logger.ANDROID
            }
            defaultRequest {
                contentType(ContentType.Application.Json)
            }
        }
    }
}