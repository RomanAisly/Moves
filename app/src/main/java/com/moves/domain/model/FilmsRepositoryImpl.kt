package com.moves.domain.model

import com.moves.data.local.FilmsDB
import com.moves.data.remote.FilmsAPI
import com.moves.domain.di.AppModule
import com.moves.utils.ResultData
import com.moves.utils.toFilmsEntity
import com.moves.utils.toLocalFilms
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

class FilmsRepositoryImpl @Inject constructor(
    private val api: FilmsAPI,
    private val db: FilmsDB
) : FilmsRepository {
    override suspend fun getFilms(page: Int, forceFetch: Boolean): Flow<ResultData<List<Films>>> {
        return flow {
            val localFilms = db.dao().getLocalFilms()
            val shouldFetch = localFilms.isNotEmpty() && !forceFetch
            if (shouldFetch) {
                emit(ResultData.Success(data = localFilms.map { filmsEntity ->
                    filmsEntity.toLocalFilms()
                }))
                return@flow
            }
            val remoteFilms = try {
                api.getFilmsByApi(apiKey = AppModule.API_KEY, page = 1)
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
                it.map { films -> films.toFilmsEntity() }
            }
            db.dao().upsertFilms(newFilms)
            emit(ResultData.Success(data = newFilms.map { it.toLocalFilms() }))
        }
    }
}