package com.moves.data.repositories

import coil.network.HttpException
import com.moves.core.utils.CheckDataResult
import com.moves.core.utils.HttpStatus
import com.moves.data.local.FilmsDB
import com.moves.data.mappers.toFilmsEntity
import com.moves.data.mappers.toLocalFilms
import com.moves.data.remote.FilmsService
import com.moves.domain.model.Films
import com.moves.domain.model.FilmsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException

class FilmsRepositoryImpl(
    private val api: FilmsService,
    private val db: FilmsDB
) : FilmsRepository {
    override suspend fun getFilms(
        category: String,
        page: Int,
        language: String,
        forceFetch: Boolean,
    ): Flow<CheckDataResult<List<Films>, HttpStatus>> {
        return flow {
            val localFilms = db.dao().getLocalFilms(category)
            val shouldFetch = localFilms.isNotEmpty() && !forceFetch
            if (shouldFetch) {
                emit(CheckDataResult.Success(data = localFilms.map { filmsEntity ->
                    filmsEntity.toLocalFilms(category)
                }))
                return@flow
            } else {
                val remoteFilms = try {
                    api.getFilms(category = category, page = page, language = language)
                } catch (e: IOException) {
                    e.printStackTrace()
                    emit(CheckDataResult.Error(error = HttpStatus.BAD_REQUEST))
                    return@flow
                } catch (e: HttpException) {
                    e.printStackTrace()
                    emit(CheckDataResult.Error(error = HttpStatus.NOT_FOUND))
                    return@flow
                } catch (e: Exception) {
                    e.printStackTrace()
                    emit(CheckDataResult.Error(error = HttpStatus.INTERNAL_SERVER_ERROR))
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

    override suspend fun getFilmById(id: Int): Flow<CheckDataResult<Films, HttpStatus>> {
        return flow {
            val filmsEntity = db.dao().getFilmByIds(id)
            emit(CheckDataResult.Success(filmsEntity.toLocalFilms(filmsEntity.category)))
            return@flow
        }
    }
}