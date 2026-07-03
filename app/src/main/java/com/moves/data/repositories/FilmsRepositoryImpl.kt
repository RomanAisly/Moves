package com.moves.data.repositories

import android.util.Log
import com.moves.core.utils.AppError
import com.moves.core.utils.CheckDataResult
import com.moves.data.local.FilmsDB
import com.moves.data.mappers.toFilmsEntity
import com.moves.data.mappers.toLocalFilms
import com.moves.data.remote.FilmsService
import com.moves.domain.model.Films
import com.moves.domain.model.FilmsRepository
import io.ktor.client.network.sockets.ConnectTimeoutException
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

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
            val localFilms = db.dao().getLocalFilms(category)
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
                    it.map { films -> films.toFilmsEntity(category) }
                }
                db.dao().upsertFilms(newFilms)
                emit(CheckDataResult.Success(data = newFilms.map { it.toLocalFilms(category) }))
            }
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
}