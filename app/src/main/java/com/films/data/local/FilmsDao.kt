package com.films.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface FilmsDao {

    @Upsert
    suspend fun upsertFilms(films: List<FilmsEntity>)

    @Query("SELECT * FROM filmsentity WHERE category = :category AND language = :language")
    suspend fun getLocalFilms(category: String, language: String): List<FilmsEntity>

    @Query("SELECT * FROM filmsentity WHERE id = :id")
    suspend fun getFilmByIds(id: Int): FilmsEntity?
}