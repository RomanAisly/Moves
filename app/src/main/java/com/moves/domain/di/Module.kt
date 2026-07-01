package com.moves.domain.di

import androidx.room.Room
import com.moves.data.local.FilmsDB
import com.moves.data.remote.FilmsService
import com.moves.domain.model.FilmsRepository
import com.moves.domain.model.FilmsRepositoryImpl
import com.moves.ui.viewmodels.DetailsScreenViewModel
import com.moves.ui.viewmodels.bottom.HomeScreenViewModel
import io.ktor.client.HttpClient
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.ANDROID
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module

val previewModule = module {
    single { HomeScreenViewModel() }
}

val networkModule = module {
    single<HttpClient> {
        HttpClient {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    prettyPrint = true
                    isLenient = true
                })
            }
            install(HttpTimeout) {
                requestTimeoutMillis = 15000L
                connectTimeoutMillis = 15000L
                socketTimeoutMillis = 15000L
            }
            install(DefaultRequest) {
                url(FilmsService.BASE_URL)
                header(HttpHeaders.ContentType, ContentType.Application.Json)
            }
            install(Logging) {
                level = LogLevel.INFO
                logger = Logger.ANDROID
            }
        }
    }
    single<FilmsService> {
        FilmsService(httpClient = get())
    }
}

val dataBaseModule = module {
    single {
        Room.databaseBuilder(
            get(),
            FilmsDB::class.java,
            "films_db"
        ).build()
    }
    single {
        get<FilmsDB>().dao()
    }
}

val viewModelsModule = module {
    single { HomeScreenViewModel() }
    single { DetailsScreenViewModel(get(), get()) }
}

val repositoriesModule = module {
    single<FilmsRepository> {
        FilmsRepositoryImpl(get(), get())
    }
}