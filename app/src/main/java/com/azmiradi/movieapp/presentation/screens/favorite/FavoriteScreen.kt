package com.azmiradi.movieapp.presentation.screens.favorite

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.azmiradi.movieapp.R
import com.azmiradi.movieapp.data.util.NavigationDestination
import com.azmiradi.movieapp.presentation.NavigationItem
import com.azmiradi.movieapp.presentation.screens.MovieItem

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun FavoriteScreen(
    viewModel: FavoriteViewModel = hiltViewModel(),
    onDetailsClick: (String) -> Unit
) {
    LaunchedEffect(key1 = Unit)
    {
        viewModel.getMovies()
    }
    Scaffold(topBar = {
        TopAppBar {
            Text(
                text =NavigationDestination.FAVORITE.title, modifier = Modifier.align(
                    Alignment.CenterVertically
                ).padding(start = 10.dp)
            )
        }
    }, content = {
        viewModel.movieState.value.data?.let {
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
                        }, onDelete = {
                            viewModel.deleteMovie(it)
                        })
                    }
                })

            AnimatedVisibility(
                visible = it.isEmpty(),
                modifier = Modifier.fillMaxSize()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.no_results),
                    contentDescription = "",

                    )
            }
        }
    })
}