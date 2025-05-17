package com.moves.domain.di

import androidx.room.Room
import com.moves.data.local.FilmsDB
import com.moves.data.remote.HttpClientFactory
import com.moves.domain.model.FilmsRepository
import com.moves.domain.model.FilmsRepositoryImpl
import com.moves.ui.viewmodels.DetailsScreenViewModel
import com.moves.ui.viewmodels.HomeScreenViewModel
import com.moves.ui.viewmodels.NowPlayingScreenViewModel
import com.moves.ui.viewmodels.TopRatedScreenViewModel
import com.moves.ui.viewmodels.UpcomingScreenViewModel
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngineFactory
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
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module
import java.util.concurrent.TimeUnit

val appModule2 = module {
    single {
        HttpClientFactory.create(OkHttp.create())
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
    single {
        FilmsRepositoryImpl(get(), get())
    }
   viewModelOf(::HomeScreenViewModel)

}