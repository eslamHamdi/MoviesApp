package com.eslam.moviesapp.data.repositories

import com.eslam.moviesapp.data.local.entities.MovieEntity
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {

    suspend fun saveFavorite(movie: MovieEntity)

    fun getFavorites(): Flow<List<MovieEntity>>

    suspend fun deleteFavorite(id:Int)
}