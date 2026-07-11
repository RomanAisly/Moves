package com.films.data.repositories

import android.util.Log
import com.films.core.utils.AppError
import com.films.core.utils.CheckDataResult
import com.films.data.local.FilmsDB
import com.films.data.mappers.toFilmsEntity
import com.films.data.mappers.toLocalFilms
import com.films.data.remote.FilmsService
import com.films.domain.model.Films
import com.films.domain.model.FilmsRepository
import com.films.domain.model.WatchProvider
import io.ktor.client.network.sockets.ConnectTimeoutException
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class FilmsRepositoryImpl(
    private val api: FilmsService,
    private val db: FilmsDB
) : FilmsRepository {
    override suspend fun getFilms(
        category: String,
        page: Int,
        language: String,
        forceFetch: Boolean,
    ): Flow<CheckDataResult<List<Films>, AppError>> {
        return flow {
            val localFilms = db.dao().getLocalFilms(category, language)
            val shouldUseLocalCache = localFilms.isNotEmpty() && !forceFetch
            if (shouldUseLocalCache) {
                emit(CheckDataResult.Success(data = localFilms.map { filmsEntity ->
                    filmsEntity.toLocalFilms(category)
                }))
                return@flow
            } else {
                val remoteFilms = try {
                    api.getFilms(category = category, page = page, language = language)
                } catch (e: UnresolvedAddressException) {
                    Log.e("FilmsRepositoryImpl", "no internet connection: ", e)
                    emit(CheckDataResult.Error(error = AppError.NO_INTERNET))
                    return@flow
                } catch (e: ConnectTimeoutException) {
                    Log.e("FilmsRepositoryImpl", "timeout: ", e)
                    emit(CheckDataResult.Error(error = AppError.TIMEOUT))
                    return@flow
                } catch (e: ClientRequestException) {
                    Log.e(
                        "FilmsRepositoryImpl",
                        "client request exception: ${e.response.status.value}",
                        e
                    )
                    val errorType = when (e.response.status.value) {
                        401 -> AppError.UNAUTHORIZED
                        404 -> AppError.NOT_FOUND
                        else -> AppError.UNKNOWN
                    }
                    emit(CheckDataResult.Error(error = errorType))
                    return@flow
                } catch (e: ServerResponseException) {
                    Log.e(
                        "FilmsRepositoryImpl",
                        "server response exception: ${e.response.status.value}",
                        e
                    )
                    emit(CheckDataResult.Error(AppError.SERVER_ERROR))
                    return@flow
                } catch (e: Exception) {
                    Log.e("FilmsRepositoryImpl", "unknown exception: ", e)
                    emit(CheckDataResult.Error(AppError.UNKNOWN))
                    return@flow
                }
                val newFilms = remoteFilms.results.let {
                    it.map { films -> films.toFilmsEntity(category, language) }
                }
                db.dao().upsertFilms(newFilms)
                emit(CheckDataResult.Success(data = newFilms.map { it.toLocalFilms(category) }))
            }
        }
    }

    override suspend fun getMovieTrailer(
        id: Int,
        language: String
    ): Flow<CheckDataResult<String?, AppError>> = flow {
        try {
            val response = api.getMovieVideos(id, language)
            val trailer =
                response.results.firstOrNull { it.site == "YouTube" && it.type == "Trailer" }
            emit(CheckDataResult.Success(trailer?.key))
        } catch (e: Exception) {
            Log.e("FilmsRepositoryImpl", "exception: ", e)
            emit(CheckDataResult.Error(AppError.UNKNOWN))
        }
    }

    override suspend fun getFilmById(id: Int): Flow<CheckDataResult<Films, AppError>> {
        return flow {
            try {
                val filmsEntity = db.dao().getFilmByIds(id)

                if (filmsEntity != null) {
                    emit(CheckDataResult.Success(filmsEntity.toLocalFilms(filmsEntity.category)))
                } else {
                    Log.e("FilmsRepositoryImpl", "Film with $id not found into DB")
                    emit(CheckDataResult.Error(AppError.NOT_FOUND))
                }

            } catch (e: Exception) {
                Log.e("FilmsRepositoryImpl", "DB exception: ", e)
                emit(CheckDataResult.Error(AppError.UNKNOWN))
            }
        }
    }

    override suspend fun getWatchProviders(
        filmId: Int,
        countryCode: String
    ): Flow<CheckDataResult<List<WatchProvider>, AppError>> = flow {
        try {
            val response = api.getWatchProviders(filmId)
            val countryData = response.results[countryCode]

            if (countryData == null) {
                emit(CheckDataResult.Success(emptyList()))
                return@flow
            }

            val countryLink = countryData.link
            val flatrateProviders = countryData.flatrate ?: emptyList()
            val buyProviders = countryData.buy ?: emptyList()

            val allProvidersDTO = (flatrateProviders + buyProviders).distinctBy { it.provider_name }

            val providersForUI = allProvidersDTO.map { dto ->
                WatchProvider(
                    name = dto.provider_name,
                    logoUrl = dto.logo_path,
                    link = countryLink
                )
            }
            emit(CheckDataResult.Success(providersForUI))

        } catch (e: Exception) {
            Log.e("FilmsRepositoryImpl", "exception: ", e)
            emit(CheckDataResult.Error(AppError.UNKNOWN))
        }
    }

    override suspend fun updateFavoriteStatus(id: Int, isFavorite: Boolean) {
        db.dao().updateFavoriteStatus(id, isFavorite)
    }

    override fun getFavoriteFilms(): Flow<List<Films>> {
        return db.dao().getFavoriteFilms().map { list ->
            list.map { it.toLocalFilms(it.category) }
        }
    }

    override suspend fun updateWatchLaterStatus(id: Int, isWatchLater: Boolean) {
        db.dao().updateWatchLaterStatus(id, isWatchLater)
    }

    override fun getWatchLaterFilms(): Flow<List<Films>> {
        return db.dao().getWatchLaterFilms().map { list ->
            list.map { it.toLocalFilms(it.category) }
        }
    }
}