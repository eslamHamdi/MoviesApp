package com.eslam.moviesapp.ui.home.di

import com.eslam.moviesapp.data.repositories.GenresRepositoryImpl
import com.eslam.moviesapp.data.repositories.MoviesRepositoryImpl
import com.eslam.moviesapp.data.repositories.RemoteDataSource
import com.eslam.moviesapp.domain.repositories.GetGenresRepository
import com.eslam.moviesapp.domain.repositories.GetMoviesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HomeModule {

    @Provides
    @Singleton
    fun providesGenresRepository(remoteDataSource: RemoteDataSource):GetGenresRepository
    {
        return GenresRepositoryImpl(remoteDataSource)
    }

    @Provides
    @Singleton
    fun providesMoviesRepository(remoteDataSource: RemoteDataSource):GetMoviesRepository
    {
        return MoviesRepositoryImpl(remoteDataSource)
    }


}