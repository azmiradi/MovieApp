package com.azmiradi.movieapp.presentation.screens.details_screen

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import com.azmiradi.movieapp.BuildConfig
import com.azmiradi.movieapp.R
import com.azmiradi.movieapp.data.util.imageBuilder
import com.azmiradi.movieapp.presentation.screens.CustomRating
import com.azmiradi.movieapp.presentation.ui.CustomContainer
import com.azmiradi.movieapp.presentation.ui.theme.YellowDark

@Composable
fun MovieDetailsScreen(movieId: String, viewModel: MovieDetailsViewModel = hiltViewModel()) {

    LaunchedEffect(movieId) {
        viewModel.getMovies(movieId)
    }
    CustomContainer(
        horizontalAlignment = Alignment.CenterHorizontally,
        baseViewModel = viewModel,
        modifier = Modifier.fillMaxSize()
    ) {
        viewModel.movieState.value.data?.let {
            Box(Modifier.fillMaxSize()) {
                Image(
                    modifier = Modifier
                        .fillMaxSize()
                        .drawWithCache {
                            val gradient = Brush.verticalGradient(
                                colors = listOf(Color.Transparent, Color.Black),
                                startY = size.height / 4,
                                endY = size.height
                            )
                            onDrawWithContent {
                                drawContent()
                                drawRect(gradient, blendMode = BlendMode.Multiply)
                            }
                        },
                    painter = rememberImagePainter(BuildConfig.IMAGE_BASE_URL + it.posterPath)
                    {
                        imageBuilder()
                    },
                    contentDescription = "",
                    contentScale = ContentScale.FillBounds
                )
                Column(
                    Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                        .padding(start = 10.dp, end = 10.dp, bottom = 20.dp)
                ) {
                    Text(
                        text = it.title ?: "-----",
                        maxLines = 3,
                        lineHeight = 20.sp,
                        color = Color.White
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = it.overview ?: "-----",
                        lineHeight = 20.sp,
                        color = Color.White,
                        modifier = Modifier.clip(
                            RoundedCornerShape(9.dp)
                        ).background(Color.Gray.copy(0.5f)).padding(5.dp)
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                        CustomRating(rating = it.voteAverage, modifier = Modifier)
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = (it.voteCount ?: "-----").toString() + " Vote",
                            maxLines = 3,
                            lineHeight = 20.sp,
                            color = Color.White
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))
                    val context = LocalContext.current
                    val movieAddedMsg = stringResource(id = R.string.movie_added)

                    Button(modifier = Modifier.align(CenterHorizontally),
                        colors = ButtonDefaults.buttonColors(YellowDark),
                        shape = RoundedCornerShape(14.dp),
                        onClick = {
                            viewModel.saveMovie(movie = it)
                            Toast.makeText(context, movieAddedMsg, Toast.LENGTH_SHORT).show()
                        }) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "",
                            tint = Color.Black
                        )
                        Text(text = "Add To Favorite")
                    }
                }


            }
        }
    }
}