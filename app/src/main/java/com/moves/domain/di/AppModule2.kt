package com.moves.domain.di

import androidx.room.Room
import com.moves.data.local.FilmsDB
import com.moves.domain.model.FilmsRepository
import com.moves.domain.model.FilmsRepositoryImpl
import com.moves.ui.viewmodels.DetailsScreenViewModel
import com.moves.ui.viewmodels.HomeScreenViewModel
import com.moves.ui.viewmodels.NowPlayingScreenViewModel
import com.moves.ui.viewmodels.TopRatedScreenViewModel
import com.moves.ui.viewmodels.UpcomingScreenViewModel
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import java.util.concurrent.TimeUnit

val appModule2 = module {
    single {
        HttpClient(OkHttp) {
            defaultRequest { url("https://api.themoviedb.org/3/") }
            install(Logging) {
                level = LogLevel.ALL
                logger = Logger.SIMPLE
            }
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                    encodeDefaults = false
                }
                )
            }
            engine {
                config {
                    retryOnConnectionFailure(true)
                   connectTimeout(5, TimeUnit.SECONDS)
                }
            }
        }
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
    viewModel { NowPlayingScreenViewModel(get()) }
    viewModel { TopRatedScreenViewModel(get()) }
    viewModel { UpcomingScreenViewModel(get()) }
}