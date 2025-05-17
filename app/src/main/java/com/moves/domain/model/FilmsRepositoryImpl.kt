package com.moves.domain.model

import com.moves.data.local.FilmsDB
import com.moves.data.remote.FilmsAPI
import com.moves.utils.CheckDataResult
import com.moves.utils.HttpStatus
import com.moves.utils.toFilmsEntity
import com.moves.utils.toLocalFilms
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException

class FilmsRepositoryImpl(
    private val api: FilmsAPI,
    private val db: FilmsDB
) : FilmsRepository {
    override suspend fun getFilms(
        category: String,
        page: Int,
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
                    api.getFilmsByApi(category = category, page = page)
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