package com.azmiradi.movieapp.data.data_source

import com.azmiradi.movieapp.data.model.Movie
import com.azmiradi.movieapp.data.model.MoviesResponse
import retrofit2.Response

interface MovieRemoteDataSource {
    suspend fun getNowPlaying(): Response<MoviesResponse>
    suspend fun getTopRated(): Response<MoviesResponse>
    suspend fun searchMovie(keyword: String): Response<MoviesResponse>
    suspend fun getMovieDetails(movieID: String): Response<Movie>
}