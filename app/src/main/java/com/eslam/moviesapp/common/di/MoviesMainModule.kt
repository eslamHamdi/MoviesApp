package com.eslam.moviesapp.common.di

import android.content.Context
import androidx.room.Room
import com.eslam.moviesapp.data.local.LocalDataSourceImpl
import com.eslam.moviesapp.data.local.MoviesDataBase
import com.eslam.moviesapp.data.remote.MoviesService
import com.eslam.moviesapp.data.remote.RemoteDataImpl
import com.eslam.moviesapp.data.repositories.FavoritesRepositoryImpl
import com.eslam.moviesapp.data.repositories.LocalDataSource
import com.eslam.moviesapp.data.repositories.RemoteDataSource
import com.eslam.moviesapp.domain.repositories.FavoritesRepository
import com.skydoves.sandwich.adapters.ApiResponseCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object MoviesMainModule {


    @Provides
    @Singleton
    fun providesRetrofit():Retrofit
    {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder().connectTimeout(60, TimeUnit.SECONDS).
        callTimeout(60, TimeUnit.SECONDS).readTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(logging).build()
        return Retrofit.Builder().baseUrl("https://api.themoviedb.org/3/").client(client)
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

    @Provides
    @Singleton
    fun providesDataBase(@ApplicationContext context: Context):MoviesDataBase
    {

        return Room.databaseBuilder(
            context,
            MoviesDataBase::class.java, "Movies-Database"
        ).build()
    }

    @Provides
    @Singleton
    fun providesLocalDataSource(moviesDataBase: MoviesDataBase): LocalDataSource
    {
        return LocalDataSourceImpl(moviesDataBase)
    }

    @Provides
    @Singleton
    fun providesFavoriteRepo(localDataSource: LocalDataSource): FavoritesRepository
    {
        return FavoritesRepositoryImpl(localDataSource)
    }
}