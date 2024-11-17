package com.moves.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface FilmsDao {

    @Upsert
    suspend fun upsertFilms(films: List<FilmsEntity>)

    @Query("SELECT * FROM filmsentity")
    suspend fun getLocalFilms(): List<FilmsEntity>
}