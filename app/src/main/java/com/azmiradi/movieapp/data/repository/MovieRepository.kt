package com.azmiradi.movieapp.data.repository

import com.azmiradi.movieapp.data.model.Movie
import com.azmiradi.movieapp.data.model.MoviesResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface MovieRepository{
    suspend fun getNowPlaying(): Response<MoviesResponse>
    suspend fun getTopRated(): Response<MoviesResponse>
    suspend fun searchMovie(keyword: String): Response<MoviesResponse>
    suspend fun insertMovie(movie: Movie):Long
    suspend fun deleteMovie(movie: Movie):Int
    fun getAllMovies(): Flow<List<Movie>>
    suspend fun getMovieDetails(movieID: String): Response<Movie>
}