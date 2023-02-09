package com.azmiradi.movieapp.data.data_source

import com.azmiradi.movieapp.data.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieLocalDataSource {
    suspend fun insertMovie(movie: Movie):Long

    suspend fun deleteMovie(movie: Movie):Int

    fun getAllMovies(): Flow<List<Movie>>
}