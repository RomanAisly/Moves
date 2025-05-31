package com.moves.domain.di

import androidx.room.Room
import com.moves.data.local.FilmsDB
import com.moves.data.remote.KtorRequest
import com.moves.domain.model.FilmsRepository
import com.moves.domain.model.FilmsRepositoryImpl
import com.moves.ui.viewmodels.DetailsScreenViewModel
import com.moves.ui.viewmodels.HomeScreenViewModel
import com.moves.ui.viewmodels.NowPlayingScreenViewModel
import com.moves.ui.viewmodels.TopRatedScreenViewModel
import com.moves.ui.viewmodels.UpcomingScreenViewModel
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single<HttpClient> {
        HttpClient(Android) {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                })
            }
            install(DefaultRequest) {
                url(KtorRequest.BASE_URL)
                header(HttpHeaders.ContentType, ContentType.Application.Json)
            }
        }
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
    viewModel { NowPlayingScreenViewModel(get()) }
    viewModel { TopRatedScreenViewModel(get()) }
    viewModel { UpcomingScreenViewModel(get()) }
}