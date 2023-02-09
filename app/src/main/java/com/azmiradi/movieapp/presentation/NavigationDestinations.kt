package com.azmiradi.movieapp.presentation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector
import com.azmiradi.movieapp.data.util.NavigationDestination

sealed class NavigationItem(var route: String, var icon: ImageVector) {
    object PlayingNow : NavigationItem(NavigationDestination.PLAYING_NOW.name, Icons.Default.PlayArrow)
    object TopRated : NavigationItem(NavigationDestination.TOP_RATED.name, Icons.Default.KeyboardArrowUp)
    object Favorite : NavigationItem(NavigationDestination.FAVORITE.name, Icons.Default.Favorite)
    object Search : NavigationItem(NavigationDestination.SEARCH.name, Icons.Default.Search)
}

