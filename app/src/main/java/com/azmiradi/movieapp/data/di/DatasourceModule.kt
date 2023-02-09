package com.azmiradi.movieapp.data.di

import com.azmiradi.movieapp.BuildConfig
import com.azmiradi.movieapp.data.data_source.MovieLocalDataSource
import com.azmiradi.movieapp.data.data_source.MovieLocalDataSourceImpl
import com.azmiradi.movieapp.data.data_source.MovieRemoteDataSource
import com.azmiradi.movieapp.data.data_source.MovieRemoteDataSourceImpl
import com.azmiradi.movieapp.data.db.MoviesDao
import com.azmiradi.movieapp.data.remote.APIServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatasourceModule {

    @Singleton
    @Provides
    fun provideMovieRemoteDatasource(apiServices: APIServices): MovieRemoteDataSource {
        return MovieRemoteDataSourceImpl(apiServices)
    }

    @Singleton
    @Provides
    fun provideMovieLocalDatasource(moviesDao: MoviesDao): MovieLocalDataSource {
        return MovieLocalDataSourceImpl(moviesDao)
    }

}