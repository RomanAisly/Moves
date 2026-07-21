package com.films.local

import androidx.room.Room
import com.films.FilmsRepository
import com.films.SettingsRepository
import com.films.remote.FilmsService
import com.films.remote.createHttpClient
import com.films.repositories.FilmsRepositoryImpl
import io.ktor.client.HttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

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
        ).fallbackToDestructiveMigrationOnDowngrade(false)
            .build()
    }
    single {
        get<FilmsDB>().dao()
    }
}

val repositoriesModule = module {
    single<FilmsRepository> {
        FilmsRepositoryImpl(get(), get())
    }
}
val settingsModule = module {
    single { androidContext().dataStore }
    single<SettingsRepository> { SettingsManager(get()) }
}
