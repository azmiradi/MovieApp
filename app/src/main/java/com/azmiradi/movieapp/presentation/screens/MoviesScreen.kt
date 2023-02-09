package com.azmiradi.movieapp.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.azmiradi.movieapp.data.util.NavigationDestination
import com.azmiradi.movieapp.presentation.ui.CustomContainer
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState

@OptIn(ExperimentalPagerApi::class)
@Composable
fun MoviesScreen(
    destination: NavigationDestination,
    viewModel: MoviesViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = Unit) {
        viewModel.getMovies(destination)
    }

    val pagerState = rememberPagerState()

    CustomContainer(baseViewModel = viewModel, modifier = Modifier.fillMaxSize()) {
        viewModel.movieState.value.data?.let {
            MovieSlider(it, onMovieClick = {
            }, pagerState = pagerState)
        }
        LazyVerticalGrid(verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp), columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 20.dp, end = 10.dp, start = 10.dp, bottom = 10.dp),
            content = {
                viewModel.movieState.value.data?.let {
                    items(it) {
                        MovieItem(movie = it, onMovieClick = { })
                    }
                }
            })
    }
}


