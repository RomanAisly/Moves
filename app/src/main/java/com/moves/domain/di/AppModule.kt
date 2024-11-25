package com.moves.domain.di

import android.content.Context
import androidx.room.Room
import com.moves.data.local.FilmsDB
import com.moves.data.remote.FilmsAPI
import com.moves.domain.model.FilmsRepository
import com.moves.domain.model.FilmsRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private const val BASE_URL = "https://api.themoviedb.org/"
    const val IMAGE_URL = "https://image.tmdb.org/t/p/w500"
    const val API_KEY = "fe4a220f82927723e66d22c09c2555ba"

    private fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .callTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }

    @Provides
    @Singleton
    fun provideFilmsApi(): FilmsAPI {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(provideOkHttpClient())
            .build()
            .create(FilmsAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideFilmsDatabase(@ApplicationContext context: Context): FilmsDB {
        return Room.databaseBuilder(
            context,
            FilmsDB::class.java,
            "films_db"
        ).build()
    }

    @Provides
    fun provideFilmsDao(db: FilmsDB) = db.dao()

    @Provides
    @Singleton
    fun provideFilmsRepository(api: FilmsAPI, db: FilmsDB): FilmsRepository {
        return FilmsRepositoryImpl(api, db)
    }
}