package com.eslam.moviesapp.domain.repositories

import com.eslam.moviesapp.domain.models.Movie
import kotlinx.coroutines.flow.Flow


//shared repository between 3 use cases ,
// not ideal as i broke interface segregation but for time constraints and project size i allowed it
//refactor to more segregated code up adding new features
interface FavoritesRepository {

    suspend fun saveFavorite(movie: Movie)

    fun getFavorites(): Flow<List<Movie>>

   suspend fun deleteFavorite(id:Int)
}