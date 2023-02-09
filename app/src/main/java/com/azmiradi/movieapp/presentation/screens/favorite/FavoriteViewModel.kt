package com.azmiradi.movieapp.presentation.screens.favorite

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.azmiradi.movieapp.data.model.Movie
import com.azmiradi.movieapp.data.util.DataState
import com.azmiradi.movieapp.data.util.NavigationDestination
import com.azmiradi.movieapp.data.util.Resource
import com.azmiradi.movieapp.data.util.resetState
import com.azmiradi.movieapp.domain.use_case.DeleteSavedMoveUseCase
import com.azmiradi.movieapp.domain.use_case.GetMoviesUseCase
import com.azmiradi.movieapp.domain.use_case.GetSavedMoviesUseCase
import com.azmiradi.movieapp.presentation.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val getSavedMoviesUseCase: GetSavedMoviesUseCase,
    private val deleteSavedMoveUseCase: DeleteSavedMoveUseCase,
) : ViewModel() {

    private var job: Job? = null
    private val _movieState = mutableStateOf(DataState<List<Movie>>())
    val movieState: State<DataState<List<Movie>>> = _movieState

    fun getMovies() {
        getSavedMoviesUseCase().onEach {
            _movieState.value = DataState(data = it)
        }.launchIn(viewModelScope)
    }

    fun deleteMovie(movie: Movie) {
        viewModelScope.launch {
            deleteSavedMoveUseCase(movie)
        }
    }
}

