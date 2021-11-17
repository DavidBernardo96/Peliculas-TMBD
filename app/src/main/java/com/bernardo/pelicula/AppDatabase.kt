package com.bernardo.pelicula

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bernardo.pelicula.data.model.MovieEntity
import com.bernardo.pelicula.domain.MoviesDao

@Database(entities = [MovieEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MoviesDao
}