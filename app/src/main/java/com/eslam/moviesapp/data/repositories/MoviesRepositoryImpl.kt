package com.eslam.moviesapp.data.repositories

import com.eslam.moviesapp.domain.models.Movie
import com.eslam.moviesapp.domain.repositories.GetMoviesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import com.eslam.moviesapp.domain.models.Result

class MoviesRepositoryImpl @Inject constructor(private val remoteDataSource: RemoteDataSource):GetMoviesRepository {
    override suspend fun getMoviesByGenre(
        page: Int,
        genre: String
    ):Flow<Result<List<Movie>>> {
        return remoteDataSource.getMoviesByGenre(genre = genre,page)
    }
}