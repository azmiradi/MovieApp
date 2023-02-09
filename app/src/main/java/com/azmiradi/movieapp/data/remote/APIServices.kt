package com.azmiradi.movieapp.data.remote

import com.azmiradi.movieapp.BuildConfig
import com.azmiradi.movieapp.data.model.Movie
import com.azmiradi.movieapp.data.model.MoviesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface APIServices {
    @GET("3/movie/now_playing?api_key=${BuildConfig.MOVIESREVIEW_API_KEY}")
    suspend fun getNowPlaying(): Response<MoviesResponse>

    @GET("3/movie/top_rated?api_key=${BuildConfig.MOVIESREVIEW_API_KEY}")
    suspend fun getTopRated(): Response<MoviesResponse>

    @GET("3/search/movie?api_key=${BuildConfig.MOVIESREVIEW_API_KEY}")
    suspend fun searchMovie(@Query("query") keyword: String): Response<MoviesResponse>

    @GET("3/movie/{movie_id}?api_key=${BuildConfig.MOVIESREVIEW_API_KEY}")
    suspend fun getMovieDetails(@Path("movie_id") movieID: String): Response<Movie>
}