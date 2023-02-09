package com.azmiradi.movieapp.domain.use_case

import com.azmiradi.movieapp.data.model.Movie
import com.azmiradi.movieapp.data.model.MoviesResponse
import com.azmiradi.movieapp.data.repository.MovieRepository
import com.azmiradi.movieapp.data.util.Constants.SUCCESS_CODE
import com.azmiradi.movieapp.data.util.NavigationDestination
import com.azmiradi.movieapp.data.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

class GetMoviesUseCase @Inject constructor(private val repository: MovieRepository) {
    operator fun invoke(keyword: String? = null, destination: NavigationDestination)
            : Flow<Resource<List<Movie?>?>> = flow {
        try {
            emit(Resource.Loading())
            val response = getRemoteData(keyword, destination)

            if (response.code() == SUCCESS_CODE) {
                response.body()?.let { it ->
                    emit(Resource.Success(it.results))
                }
                return@flow
            } else {
                response.errorBody()?.let {
                    emit(Resource.Error(it.string()))
                    return@flow
                }
                emit(Resource.Error(response.message()))
            }

        } catch (http: HttpException) {
            emit(Resource.Error(http.message()))
        } catch (io: IOException) {
            emit(Resource.Error(io.message ?: "Error occur"))
        }
    }.flowOn(Dispatchers.IO)

    private suspend fun getRemoteData(
        keyword: String?,
        destination: NavigationDestination
    ): Response<MoviesResponse> {
        return when (destination) {
            NavigationDestination.SEARCH -> {
                repository.searchMovie(keyword ?: "")
            }
            NavigationDestination.TOP_RATED -> {
                repository.getTopRated()
            }
            NavigationDestination.PLAYING_NOW -> {
                repository.getNowPlaying()
            }
            else -> {
                repository.getNowPlaying()
            }
        }
    }
}