package com.azmiradi.movieapp.presentation.screens.details_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.azmiradi.movieapp.data.model.Movie
import com.azmiradi.movieapp.data.util.DataState
import com.azmiradi.movieapp.data.util.Resource
import com.azmiradi.movieapp.data.util.resetState
import com.azmiradi.movieapp.domain.use_case.GetMovieDetailsUseCase
import com.azmiradi.movieapp.domain.use_case.SaveMoveUseCase
import com.azmiradi.movieapp.presentation.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val movieDetailsUseCase: GetMovieDetailsUseCase,
    private val saveMoveUseCase: SaveMoveUseCase
) : BaseViewModel() {

    private var job: Job? = null
    private val _movieState = mutableStateOf(DataState<Movie>())
    val movieState: State<DataState<Movie>> = _movieState

    fun getMovies(movieID: String) {
        job?.cancel()
        job = movieDetailsUseCase(movieID = movieID).onEach {
            when (it) {
                is Resource.Loading -> {
                    _movieState.value = DataState(isLoading = true)
                }

                is Resource.Error -> {
                    _movieState.value = DataState(error = it.message ?: "Error Occur!")
                }

                is Resource.Success -> {
                    _movieState.value = DataState(data = it.data)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun saveMovie(movie: Movie){
        viewModelScope.launch {
            saveMoveUseCase(movie)
        }
    }

    override fun resetState() {
        job?.cancel()
        _movieState.resetState()
    }

    override fun isLoading(): Boolean {
        return _movieState.value.isLoading
    }

    override fun toastMessage(): String {
        return _movieState.value.error
    }
}

