package com.moves.domain.di

import androidx.room.Room
import com.moves.data.local.FilmsDB
import com.moves.data.remote.FilmsAPIRequest
import com.moves.domain.model.FilmsRepository
import com.moves.domain.model.FilmsRepositoryImpl
import com.moves.ui.viewmodels.DetailsScreenViewModel
import com.moves.ui.viewmodels.HomeScreenViewModel
import com.moves.ui.viewmodels.NowPlayingScreenViewModel
import com.moves.ui.viewmodels.TopRatedScreenViewModel
import com.moves.ui.viewmodels.UpcomingScreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {
    single {
        Retrofit.Builder()
            .baseUrl(FilmsAPIRequest.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(FilmsAPIRequest::class.java)
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