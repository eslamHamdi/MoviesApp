package com.eslam.moviesapp.data.local

import com.eslam.moviesapp.data.local.entities.MovieEntity
import com.eslam.moviesapp.data.repositories.LocalDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(private val moviesDataBase: MoviesDataBase):LocalDataSource {
    override suspend fun saveFavorite(movie: MovieEntity) {
        moviesDataBase.getFavoritesDao().saveMovie(movie)
    }

    override fun getFavorites(): Flow<List<MovieEntity>> {
        return moviesDataBase.getFavoritesDao().getAllFavorites()
    }

    override suspend fun deleteFavorite(id: Int) {
        moviesDataBase.getFavoritesDao().deleteMovie(id)
    }
}