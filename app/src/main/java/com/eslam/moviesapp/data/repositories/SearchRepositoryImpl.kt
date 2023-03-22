package com.eslam.moviesapp.data.repositories

import com.eslam.moviesapp.domain.models.Movie
import com.eslam.moviesapp.domain.models.Result
import com.eslam.moviesapp.domain.repositories.MovieSearchRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(private val remoteDataSource: RemoteDataSource):MovieSearchRepository {
    override suspend fun getMoviesByKeyWord(query: String): Flow<Result<List<Movie>>> {
       return remoteDataSource.searchMoviesByKewWord(query,1)
    }


}