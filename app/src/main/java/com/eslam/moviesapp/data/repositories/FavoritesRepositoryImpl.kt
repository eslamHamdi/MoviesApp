package com.eslam.moviesapp.data.repositories

import com.eslam.moviesapp.data.local.entities.MovieEntity
import com.eslam.moviesapp.data.local.entities.toMovie
import com.eslam.moviesapp.domain.models.Movie
import com.eslam.moviesapp.domain.repositories.FavoritesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FavoritesRepositoryImpl @Inject constructor(private val localDataSource: LocalDataSource):FavoritesRepository {
    override suspend fun saveFavorite(movie: Movie) {
        localDataSource.saveFavorite(MovieEntity(movie.id,movie.overview,movie.title,movie.genreId,
        movie.posterPath,movie.releaseDate,movie.voteAverage,movie.voteCount,movie.isFavorite,movie.genre))
    }

    override fun getFavorites(): Flow<List<Movie>> {
        return localDataSource.getFavorites().map {
            it.toMovie()
        }
    }
}