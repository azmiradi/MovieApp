package com.azmiradi.movieapp.domain.use_case

import com.azmiradi.movieapp.data.model.Movie
import com.azmiradi.movieapp.data.repository.MovieRepository
import javax.inject.Inject

class DeleteSavedMoveUseCase @Inject constructor(private val repository: MovieRepository) {
    suspend operator fun invoke(movie: Movie) = repository.deleteMovie(movie)
}