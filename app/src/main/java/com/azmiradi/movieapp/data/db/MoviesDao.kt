package com.azmiradi.movieapp.data.db

import androidx.room.*
import com.azmiradi.movieapp.data.model.Movie
import kotlinx.coroutines.flow.Flow

@Dao
interface MoviesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: Movie):Long

    @Query("Select * from Movie")
    fun getAllMovies():Flow<List<Movie>>

    @Delete
    suspend fun deleteMovies(movie: Movie):Int
}