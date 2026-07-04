package com.films.core.di

import androidx.room.Room
import com.films.core.network.createHttpClient
import com.films.data.local.FilmsDB
import com.films.data.remote.FilmsService
import com.films.data.repositories.FilmsRepositoryImpl
import com.films.domain.model.FilmsRepository
import com.films.ui.screens.details.DetailsScreenViewModel
import com.films.ui.screens.home.HomeScreenViewModel
import io.ktor.client.HttpClient
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val previewModule = module {
    single { }
}

val networkModule = module {
    single<HttpClient> {
        createHttpClient()
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
        ).fallbackToDestructiveMigration(false).build()
    }
    single {
        get<FilmsDB>().dao()
    }
}

val viewModelsModule = module {
    viewModelOf(::HomeScreenViewModel)
    viewModelOf(::DetailsScreenViewModel)
}

val repositoriesModule = module {
    single<FilmsRepository> {
        FilmsRepositoryImpl(get(), get())
    }
}