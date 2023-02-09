package com.azmiradi.movieapp.data.di

import android.app.Application
import androidx.room.Room
import com.azmiradi.movieapp.data.db.Database
import com.azmiradi.movieapp.data.db.MoviesDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(app:Application): Database {
        return Room.databaseBuilder(app, Database::class.java, "movie_db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideArticle(database: Database): MoviesDao {
        return database.getMovieDao()
    }


}