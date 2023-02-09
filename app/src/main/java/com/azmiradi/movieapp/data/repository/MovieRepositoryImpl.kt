package com.azmiradi.movieapp.data.repository

import com.azmiradi.movieapp.data.data_source.MovieLocalDataSource
import com.azmiradi.movieapp.data.data_source.MovieRemoteDataSource
import com.azmiradi.movieapp.data.model.Movie
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val movieRemoteDataSource: MovieRemoteDataSource,
    private val movieLocalDataSource: MovieLocalDataSource
) : MovieRepository {
    override suspend fun getNowPlaying() = movieRemoteDataSource.getNowPlaying()
    override suspend fun getTopRated() = movieRemoteDataSource.getTopRated()
    override suspend fun searchMovie(keyword: String) = movieRemoteDataSource.searchMovie(keyword)
    override suspend fun insertMovie(movie: Movie) = movieLocalDataSource.insertMovie(movie)
    override suspend fun deleteMovie(movie: Movie) = movieLocalDataSource.deleteMovie(movie)
    override fun getAllMovies(): Flow<List<Movie>> = movieLocalDataSource.getAllMovies()
    override suspend fun getMovieDetails(movieID: String) =
        movieRemoteDataSource.getMovieDetails(movieID)
}