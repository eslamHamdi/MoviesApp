package com.eslam.moviesapp.domain.repositories

import com.eslam.moviesapp.domain.models.Movie
import kotlinx.coroutines.flow.Flow

interface FavoritesRepository {

    suspend fun saveFavorite(movie: Movie)

    fun getFavorites(): Flow<List<Movie>>
}