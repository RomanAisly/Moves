package com.moves.domain.model

import com.moves.data.local.FilmsDB
import com.moves.data.remote.FilmsAPI
import com.moves.utils.ResultData
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
    ): Flow<ResultData<List<Films>>> {
        return flow {
            val localFilms = db.dao().getLocalFilms(category)
            val shouldFetch = localFilms.isNotEmpty() && !forceFetch
            if (shouldFetch) {
                emit(ResultData.Success(data = localFilms.map { filmsEntity ->
                    filmsEntity.toLocalFilms(category)
                }))
                return@flow
            }
            val remoteFilms = try {
                api.getFilmsByApi(category = category, page = page)
            } catch (e: IOException) {
                e.printStackTrace()
                emit(ResultData.Error(message = "Network error: ${e.message}"))
                return@flow
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(ResultData.Error(message = "Http error: ${e.localizedMessage}"))
                return@flow
            } catch (e: Exception) {
                e.printStackTrace()
                emit(ResultData.Error(message = "Unknown error: ${e.message}"))
                return@flow
            }
            val newFilms = remoteFilms.results.let {
                it.map { films -> films.toFilmsEntity(category) }
            }
            db.dao().upsertFilms(newFilms)
            emit(ResultData.Success(data = newFilms.map { it.toLocalFilms(category) }))
        }
    }

    override suspend fun getFilmById(id: Int): Flow<ResultData<Films>> {
        return flow {
            val filmsEntity = db.dao().getFilmByIds(id)
            if (filmsEntity != null) {
                emit(ResultData.Success(filmsEntity.toLocalFilms(filmsEntity.category)))
                return@flow
            }
            emit(ResultData.Error(message = "Error: no such film"))
        }
    }
}