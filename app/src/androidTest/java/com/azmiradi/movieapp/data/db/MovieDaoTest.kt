package com.azmiradi.movieapp.data.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.azmiradi.movieapp.data.model.Movie
import com.google.common.truth.Truth
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MovieDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    private lateinit var moviesDao: MoviesDao
    private lateinit var appDatabase: Database


    @Before
    fun setUp() {
        appDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(), Database::class.java
        )
            .build()
        moviesDao = appDatabase.getMovieDao()
    }


    @Test
    fun saveArticleTest() = runBlocking {
        // in source object must name some id to test because converter return name in id and name
        val movie = Movie(
            "Romantic Store", "/nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg", 2.3f,
            1, 200,
            "Interstellar chronicles the adventures of a group of explorers who make use of a newly discovered wormhole to surpass the limitations on human space travel and conquer the vast distances involved in an interstellar voyage.",
        )

        moviesDao.insertMovie(movie)
        val elements = moviesDao.getAllMovies().take(1).toList()
        Truth.assertThat(elements[0][0].id).isEqualTo(movie.id)
    }

    @Test
    fun deleteArticleTest() = runBlocking {
        // in source object must name some id to test because converter return name in id and name
        val movie = Movie(
            "Romantic Store", "/nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg", 2.3f,
            1, 200,
            "Interstellar chronicles the adventures of a group of explorers who make use of a newly discovered wormhole to surpass the limitations on human space travel and conquer the vast distances involved in an interstellar voyage.",
        )

        moviesDao.insertMovie(movie)
        moviesDao.deleteMovies(movie)

        val elements = moviesDao.getAllMovies().take(1).toList()

        Truth.assertThat(elements[0]).isEmpty()
    }

    @After
    fun tearDown() {
        appDatabase.close()
    }
}
