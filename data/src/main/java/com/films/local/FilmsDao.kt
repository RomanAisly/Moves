package com.films.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface FilmsDao {

    @Upsert
    suspend fun upsertFilms(films: List<FilmsEntity>)

    @Query("SELECT * FROM filmsentity WHERE category = :category AND language = :language")
    suspend fun getLocalFilms(category: String, language: String): List<FilmsEntity>

    @Query("SELECT * FROM filmsentity WHERE id = :id")
    suspend fun getFilmById(id: Int): FilmsEntity?

    @Query("SELECT * FROM filmsentity WHERE id IN (:ids)")
    suspend fun getFilmsByIds(ids: List<Int>): List<FilmsEntity>

    @Query("UPDATE filmsentity SET isFavorite = :isFavorite WHERE id = :id")
    suspend fun updateFavoriteStatus(id: Int, isFavorite: Boolean)

    @Query("SELECT * FROM filmsentity WHERE isFavorite = 1")
    fun getFavoriteFilms(): Flow<List<FilmsEntity>>

    @Query("UPDATE filmsentity SET isWatchLater = :isWatchLater WHERE id = :id")
    suspend fun updateWatchLaterStatus(id: Int, isWatchLater: Boolean)

    @Query("SELECT * FROM filmsentity WHERE isWatchLater = 1")
    fun getWatchLaterFilms(): Flow<List<FilmsEntity>>
}