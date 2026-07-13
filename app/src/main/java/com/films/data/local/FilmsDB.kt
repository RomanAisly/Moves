package com.films.data.local

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [FilmsEntity::class],
    version = 7,
    exportSchema = true,
    autoMigrations = [AutoMigration(from = 6, to = 7)]
)
abstract class FilmsDB : RoomDatabase() {
    abstract fun dao(): FilmsDao
}