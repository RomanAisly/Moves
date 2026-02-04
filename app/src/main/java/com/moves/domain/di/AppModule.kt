package com.moves.domain.di

import androidx.room.Room
import com.moves.data.local.FilmsDB
import com.moves.data.remote.KtorRequest
import com.moves.domain.model.FilmsRepository
import com.moves.domain.model.FilmsRepositoryImpl
import com.moves.ui.viewmodels.DetailsScreenViewModel
import com.moves.ui.viewmodels.HomeScreenViewModel
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
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
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import org.koin.dsl.onClose

val appModule = module {
    single<HttpClient> {
        HttpClient(Android) {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    prettyPrint = true
                    isLenient = true
                })
            }
            install(HttpTimeout){
                requestTimeoutMillis = 15000L
                connectTimeoutMillis = 15000L
                socketTimeoutMillis = 15000L
            }
            install(DefaultRequest) {
                url(KtorRequest.BASE_URL)
                header(HttpHeaders.ContentType, ContentType.Application.Json)
            }
            install(Logging){
                level = LogLevel.INFO
                logger = Logger.ANDROID
            }
        }
    } onClose {
        it?.close()
    }

    single<KtorRequest> {
        KtorRequest(httpClient = get())
    }

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

    single<FilmsRepository> {
        FilmsRepositoryImpl(get(), get())
    }

    viewModel { HomeScreenViewModel(get()) }
    viewModel { DetailsScreenViewModel(get(), get()) }
}