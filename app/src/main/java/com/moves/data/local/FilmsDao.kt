package com.moves.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface FilmsDao {

    @Upsert
    suspend fun upsertFilms(films: List<FilmsEntity>)

    @Query("SELECT * FROM filmsentity WHERE category = :category")
    suspend fun getLocalFilms(category: String): List<FilmsEntity>
}