package com.azmiradi.movieapp.data.di

import com.azmiradi.movieapp.data.data_source.MovieLocalDataSource
import com.azmiradi.movieapp.data.data_source.MovieRemoteDataSource
import com.azmiradi.movieapp.data.data_source.MovieRemoteDataSourceImpl
import com.azmiradi.movieapp.data.remote.APIServices
import com.azmiradi.movieapp.data.repository.MovieRepository
import com.azmiradi.movieapp.data.repository.MovieRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideMovieRepository(
        movieRemoteDataSource: MovieRemoteDataSource,
        movieLocalDataSource: MovieLocalDataSource
    ): MovieRepository {
        return MovieRepositoryImpl(movieRemoteDataSource, movieLocalDataSource)
    }

}