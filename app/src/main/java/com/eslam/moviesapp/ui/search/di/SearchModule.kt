package com.eslam.moviesapp.ui.search.di

import com.eslam.moviesapp.data.repositories.RemoteDataSource
import com.eslam.moviesapp.data.repositories.SearchRepositoryImpl
import com.eslam.moviesapp.domain.repositories.MovieSearchRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object SearchModule {

    @Provides
    @Singleton
    fun providesSearchRepo(remoteDataSource: RemoteDataSource):MovieSearchRepository
    {
        return SearchRepositoryImpl(remoteDataSource)
    }
}