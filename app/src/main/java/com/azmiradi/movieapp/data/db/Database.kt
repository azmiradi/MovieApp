package com.azmiradi.movieapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.azmiradi.movieapp.data.model.Movie

@Database(entities = [Movie::class],version = 3,exportSchema = false)
abstract  class Database : RoomDatabase() {
    abstract fun getMovieDao(): MoviesDao
}