package com.azmiradi.movieapp.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.azmiradi.movieapp.BuildConfig.IMAGE_BASE_URL
import com.azmiradi.movieapp.data.model.Movie
import com.azmiradi.movieapp.data.util.imageBuilder
import com.azmiradi.movieapp.presentation.ui.theme.RED_CUSTOM
import com.gowtham.ratingbar.RatingBar
import com.gowtham.ratingbar.RatingBarConfig
import com.gowtham.ratingbar.RatingBarStyle
import com.gowtham.ratingbar.StepSize

@Composable
fun MovieItem(
    movie: Movie,
    onMovieClick: () -> Unit,
    onDelete: (() -> Unit)? = null,
    isSlider: Boolean = false,
) {
    Card(
        modifier = Modifier
            .clickable {
                onMovieClick()
            },
        elevation = 10.dp,
        shape = if (isSlider) RoundedCornerShape(0.dp) else RoundedCornerShape(15.dp),
    ) {
        Box(
            modifier = Modifier
                .height(240.dp)
                .background(Color.Black)
        ) {
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
                painter = rememberImagePainter(IMAGE_BASE_URL + movie.posterPath)
                {
                    imageBuilder()
                },
                contentDescription = "",
                contentScale = ContentScale.FillBounds
            )
            Text(
                text = movie.title ?: "-----",
                maxLines = 3,
                lineHeight = 20.sp,
                color = Color.White,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(start = 10.dp, end = 10.dp, bottom = 25.dp)
            )


            if (!isSlider)
                CustomRating(
                    rating = movie.voteAverage,
                    modifier = Modifier
                        .padding(10.dp)
                        .align(Alignment.BottomCenter),
                )

              onDelete?.let {
                    IconButton(
                        onClick = it,
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(15.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "",
                            tint = RED_CUSTOM,
                        )
                    }
                }



//            if (isDetails) {
//                val genres= remember {
//                    movie.genres?.joinToString {
//                        it.name+", "
//                    }
//                }
//
//                Text(
//                    text = genres ?: "-----",
//                    maxLines = 1,
//                    lineHeight = 16.sp,
//                    color = Color.White,
//                    modifier = Modifier
//                        .align(Alignment.TopCenter)
//                        .padding(start = 10.dp, end = 10.dp, bottom = 25.dp)
//                )
//            }
        }

    }
}


@Composable
fun CustomRating(rating: Float?, modifier: Modifier) {
    RatingBar(modifier = modifier,
        value = (rating?.div(2) ?: 0f),
        config = RatingBarConfig()
            .activeColor(Color.Yellow)
            .hideInactiveStars(false)
            .inactiveColor(Color.LightGray)
            .inactiveBorderColor(Color.Red)
            .stepSize(StepSize.HALF)
            .numStars(5)
            .isIndicator(true)
            .size(15.dp)
            .style(RatingBarStyle.HighLighted),
        onRatingChanged = {},
        onValueChange = {}
    )
}