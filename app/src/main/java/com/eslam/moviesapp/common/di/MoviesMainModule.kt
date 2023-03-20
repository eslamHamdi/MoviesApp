package com.eslam.moviesapp.common.di

import com.eslam.moviesapp.data.remote.MoviesService
import com.eslam.moviesapp.data.remote.RemoteDataImpl
import com.eslam.moviesapp.data.repositories.RemoteDataSource
import com.skydoves.sandwich.adapters.ApiResponseCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object MoviesMainModule {


    @Provides
    @Singleton
    fun providesRetrofit():Retrofit
    {
        return Retrofit.Builder().baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(ApiResponseCallAdapterFactory.create()).build()
    }

    @Provides
    @Singleton
    fun providesMoviesService(retrofit: Retrofit):MoviesService
    {
        return retrofit.create(MoviesService::class.java)
    }

    @Provides
    @Singleton
    fun providesRemoteDataSource(moviesService: MoviesService): RemoteDataSource
    {
        return RemoteDataImpl(moviesService)
    }
}