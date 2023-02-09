package com.azmiradi.movieapp.domain.use_case

import com.azmiradi.movieapp.data.model.Movie
import com.azmiradi.movieapp.data.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSavedMoviesUseCase @Inject constructor(private val repository: MovieRepository) {
      operator fun invoke():Flow<List<Movie>>{
          return repository.getAllMovies()
      }

}