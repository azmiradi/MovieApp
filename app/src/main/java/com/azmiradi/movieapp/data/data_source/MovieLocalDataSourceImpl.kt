package com.azmiradi.movieapp.data.data_source

import com.azmiradi.movieapp.data.db.MoviesDao
import com.azmiradi.movieapp.data.model.Movie
import com.azmiradi.movieapp.data.remote.APIServices
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieLocalDataSourceImpl @Inject constructor(private val moveDao: MoviesDao) :
    MovieLocalDataSource {
    override suspend fun insertMovie(movie: Movie)= moveDao.insertMovie(movie)

    override suspend fun deleteMovie(movie: Movie) = moveDao.deleteMovies(movie)

    override fun getAllMovies(): Flow<List<Movie>> = moveDao.getAllMovies()
}