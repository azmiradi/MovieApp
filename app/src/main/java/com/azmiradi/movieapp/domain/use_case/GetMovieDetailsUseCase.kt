package com.azmiradi.movieapp.domain.use_case

import com.azmiradi.movieapp.data.model.Movie
import com.azmiradi.movieapp.data.repository.MovieRepository
import com.azmiradi.movieapp.data.util.Constants.SUCCESS_CODE
import com.azmiradi.movieapp.data.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetMovieDetailsUseCase @Inject constructor(private val repository: MovieRepository) {
    operator fun invoke(movieID: String): Flow<Resource<Movie>> = flow {
        try {
            emit(Resource.Loading())
            val response = repository.getMovieDetails(movieID)

            if (response.code() == SUCCESS_CODE) {
                response.body()?.let { it ->
                    emit(Resource.Success(it))
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
}