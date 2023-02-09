package com.azmiradi.movieapp.presentation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.plusAssign
import com.azmiradi.movieapp.data.util.Constants.DETAILS_DESTINATION
import com.azmiradi.movieapp.data.util.NavigationDestination
import com.azmiradi.movieapp.presentation.screens.details_screen.MovieDetailsScreen
import com.azmiradi.movieapp.presentation.screens.favorite.FavoriteScreen
import com.azmiradi.movieapp.presentation.screens.movie_screen.MoviesScreen
import com.azmiradi.movieapp.presentation.ui.theme.BlueDark
import com.azmiradi.movieapp.presentation.ui.theme.GrayOpen
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.ModalBottomSheetLayout
import com.google.accompanist.navigation.material.bottomSheet
import com.google.accompanist.navigation.material.rememberBottomSheetNavigator

@Composable
fun NavigationScreen() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = { BottomNavigationBar(navController) }
    ) { innerPadding ->
        NavigationControl(navController, innerPadding)
    }

}


@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(
        NavigationItem.PlayingNow,
        NavigationItem.TopRated,
        NavigationItem.Favorite,
        NavigationItem.Search
    )
    BottomNavigation(
        backgroundColor = GrayOpen,
        contentColor = GrayOpen
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route


        items.forEach { item ->
            BottomNavigationItem(
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = ""
                    )
                },
                selectedContentColor = BlueDark,
                unselectedContentColor = Color.Gray.copy(0.4f),
                alwaysShowLabel = false,
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        // Pop up to the start destination of the graph to
                        // avoid building up a large stack of destinations
                        // on the back stack as users select items
                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) {
                                saveState = true
                            }
                        }
                        // Avoid multiple copies of the same destination when
                        // reselecting the same item
                        launchSingleTop = true
                        // Restore state when reselecting a previously selected item
                        restoreState = true
                    }
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterialNavigationApi::class)
@Composable
fun NavigationControl(
    navController: NavHostController,
    innerPadding: PaddingValues
) {
    val bottomSheetNavigator = rememberBottomSheetNavigator()
    navController.navigatorProvider += bottomSheetNavigator

    ModalBottomSheetLayout(
        bottomSheetNavigator = bottomSheetNavigator,
        modifier = Modifier.padding(innerPadding)
    ) {
        NavHost(
            navController, startDestination = NavigationItem.PlayingNow.route,

            ) {
            composable(NavigationItem.TopRated.route) {
                MoviesScreen(NavigationDestination.TOP_RATED)
                {
                    navController.navigate(DETAILS_DESTINATION.replace("{movie_id}", it))
                }
            }

            composable(NavigationItem.Favorite.route) {
                FavoriteScreen() {
                    navController.navigate(DETAILS_DESTINATION.replace("{movie_id}", it))
                }
            }

            composable(NavigationItem.PlayingNow.route) {
                MoviesScreen(NavigationDestination.PLAYING_NOW)
                {
                    navController.navigate(DETAILS_DESTINATION.replace("{movie_id}", it))
                }
            }

            composable(NavigationItem.Search.route) {
                MoviesScreen(NavigationDestination.SEARCH)
                {
                    navController.navigate(DETAILS_DESTINATION.replace("{movie_id}", it))
                }
            }

            bottomSheet(route = DETAILS_DESTINATION) { backStackEntry ->
                val movieId = backStackEntry.arguments?.getString("movie_id") ?: "1"
                MovieDetailsScreen(movieId)
            }

        }
    }

}

