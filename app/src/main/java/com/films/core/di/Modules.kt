package com.films.core.di

import androidx.room.Room
import com.films.core.network.createHttpClient
import com.films.data.local.FilmsDB
import com.films.data.local.SettingsManager
import com.films.data.local.dataStore
import com.films.data.remote.FilmsService
import com.films.data.repositories.FilmsRepositoryImpl
import com.films.domain.model.FilmsRepository
import com.films.ui.screens.details.DetailsViewModel
import com.films.ui.screens.favorites.FavoritesViewModel
import com.films.ui.screens.home.HomeViewModel
import com.films.ui.screens.settings.SettingsViewModel
import com.films.ui.screens.watch_later.WatchLaterViewModel
import io.ktor.client.HttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val previewModule = module {

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
        ).build()
    }
    single {
        get<FilmsDB>().dao()
    }
}

val viewModelsModule = module {
    viewModelOf(::HomeViewModel)
    viewModelOf(::DetailsViewModel)
    viewModelOf(::FavoritesViewModel)
    viewModelOf(::WatchLaterViewModel)
    viewModelOf(::SettingsViewModel)
}

val repositoriesModule = module {
    single<FilmsRepository> {
        FilmsRepositoryImpl(get(), get())
    }
}
val settingsModule = module {
    single { androidContext().dataStore }
    single { SettingsManager(get()) }
}
