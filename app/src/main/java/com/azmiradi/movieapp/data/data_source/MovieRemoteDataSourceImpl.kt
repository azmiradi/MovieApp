package com.azmiradi.movieapp.data.data_source

import com.azmiradi.movieapp.data.remote.APIServices
import retrofit2.Response
import javax.inject.Inject

class MovieRemoteDataSourceImpl @Inject constructor(private val apiServices: APIServices) :
    MovieRemoteDataSource {
    override suspend fun getNowPlaying() = apiServices.getNowPlaying()
    override suspend fun getTopRated() = apiServices.getTopRated()
    override suspend fun searchMovie(keyword: String) = apiServices.searchMovie(keyword)
    override suspend fun getMovieDetails(movieID: String) = apiServices.getMovieDetails(movieID)
}