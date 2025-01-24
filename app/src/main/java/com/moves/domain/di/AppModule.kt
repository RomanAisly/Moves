package com.moves.domain.di

import androidx.room.Room
import com.moves.data.local.FilmsDB
import com.moves.data.remote.FilmsAPI
import com.moves.domain.model.FilmsRepository
import com.moves.domain.model.FilmsRepositoryImpl
import com.moves.ui.viewmodels.DetailsScreenViewModel
import com.moves.ui.viewmodels.HomeScreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {
    single {
        Retrofit.Builder()
            .baseUrl(FilmsAPI.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(FilmsAPI::class.java)
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