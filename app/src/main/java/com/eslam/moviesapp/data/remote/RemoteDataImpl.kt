package com.eslam.moviesapp.data.remote

import com.eslam.moviesapp.data.repositories.RemoteDataSource
import com.eslam.moviesapp.domain.models.Genre
import com.eslam.moviesapp.domain.models.Movie
import com.eslam.moviesapp.domain.models.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RemoteDataImpl @Inject constructor(private val moviesService: MoviesService):RemoteDataSource {
    override suspend fun getGenres(): Flow<Result<List<Genre>>> {
        return flow {
            emit(Result.Loading())

            val result = moviesService.getAllGenres()
        }
    }

    override suspend fun getMoviesByGenre(genre: String,page:Int):Flow<Result<List<Movie>>> {
        return flow {
            emit(Result.Loading())

            val result = moviesService.getMoviesListByGenre(page =page, genre = genre)
        }
    }

    override suspend fun searchMoviesByKewWord(
        query: String,
        page: Int
    ): Flow<Result<List<Movie>>> {

        return flow {
            emit(Result.Loading())

            val result = moviesService.searchByKeyWord(kewWord = query, page = page)
        }

    }
}