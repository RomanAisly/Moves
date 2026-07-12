package com.films.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [FilmsEntity::class], version = 6, exportSchema = false)
abstract class FilmsDB : RoomDatabase() {
    abstract fun dao(): FilmsDao
}