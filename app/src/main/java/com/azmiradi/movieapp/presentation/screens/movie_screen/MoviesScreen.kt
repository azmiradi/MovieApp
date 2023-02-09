package com.azmiradi.movieapp.presentation.screens.movie_screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.azmiradi.movieapp.R
import com.azmiradi.movieapp.data.util.NavigationDestination
import com.azmiradi.movieapp.presentation.screens.MovieItem
import com.azmiradi.movieapp.presentation.screens.MovieSlider
import com.azmiradi.movieapp.presentation.ui.CustomContainer
import com.azmiradi.movieapp.presentation.ui.SearchBar
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState

@OptIn(
    ExperimentalPagerApi::class,
    ExperimentalAnimationApi::class,
    ExperimentalComposeUiApi::class
)
@Composable
fun MoviesScreen(
    destination: NavigationDestination,
    viewModel: MoviesViewModel = hiltViewModel(),
    onDetailsClick: (String) -> Unit
) {
    val pagerState = rememberPagerState()
    val searchKeyword = rememberSaveable {
        mutableStateOf("")
    }

    if (destination != NavigationDestination.SEARCH) {
        LaunchedEffect(key1 = Unit) {
            viewModel.getMovies(destination)
        }
    }
    CustomContainer(
        horizontalAlignment = CenterHorizontally, baseViewModel = viewModel,
        modifier = Modifier.fillMaxSize(),
        topBar = {
            if (destination == NavigationDestination.SEARCH) {
                SearchBar(
                    searchText = searchKeyword.value,
                    placeholderText = "Search Movies",
                    onSearchTextChanged = {
                        searchKeyword.value = it
                    },
                    onClearClick = {
                        viewModel.resetState()
                        searchKeyword.value = ""
                    },
                    startSearch = {
                        viewModel.getMovies(destination, keyword = searchKeyword.value)
                    })
                Spacer(modifier = Modifier.height(10.dp))
            } else {
                TopAppBar {
                    Text(
                        text = destination.title, modifier = Modifier.align(
                            CenterVertically
                        ).padding(start = 10.dp)
                    )
                }
            }
        }
    ) {

        viewModel.movieState.value.data?.let {
            AnimatedVisibility(
                visible = it.isNotEmpty(),
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
            ) {
                Column {
                    MovieSlider(it, onMovieClick = {
                    }, pagerState = pagerState)

                    LazyVerticalGrid(verticalArrangement = Arrangement.spacedBy(10.dp),
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        columns = GridCells.Fixed(2),
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 20.dp, end = 10.dp, start = 10.dp, bottom = 10.dp),
                        content = {
                            items(it) {
                                MovieItem(movie = it, onMovieClick = {
                                    onDetailsClick(it.id.toString())
                                })
                            }
                        })
                }
            }

            AnimatedVisibility(
                visible = it.isEmpty(),
            ) {
                Image(
                    painter = painterResource(id = R.drawable.no_results),
                    contentDescription = "",

                    )
            }
        }

    }
}


