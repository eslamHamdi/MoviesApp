package com.eslam.moviesapp.data.remote

import com.eslam.moviesapp.data.remote.dto.toGenre
import com.eslam.moviesapp.data.remote.dto.toMovies
import com.eslam.moviesapp.data.repositories.RemoteDataSource
import com.eslam.moviesapp.domain.models.Genre
import com.eslam.moviesapp.domain.models.Movie
import com.eslam.moviesapp.domain.models.Result
import com.skydoves.sandwich.message
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnException
import com.skydoves.sandwich.suspendOnSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class RemoteDataImpl @Inject constructor(private val moviesService: MoviesService):RemoteDataSource {
    override suspend fun getGenres(): Flow<Result<List<Genre>>> {
        return flow {
            emit(Result.Loading())

            val result = moviesService.getAllGenres()

            result.suspendOnSuccess {

                emit(Result.Success(this.data.genres.toGenre()))
            }.suspendOnError {
                emit(Result.Failure(this.message(),this.statusCode.code))
            }.suspendOnException {
                emit(Result.Failure(this.message,0))
            }


        }.catch {
            emit(Result.Failure("Connection Error",0))
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getMoviesByGenre(genre: String,page:Int):Flow<Result<List<Movie>>> {
        return flow<Result<List<Movie>>> {
            emit(Result.Loading())

            val result = moviesService.getMoviesListByGenre(page =page, genre = genre)

            result.suspendOnSuccess {
                emit(Result.Success(this.data.results.toMovies()))
            }.suspendOnError {
                emit(Result.Failure(this.message(),this.statusCode.code))
            }.suspendOnException {
                emit(Result.Failure(this.message,0))
            }
        }.catch {
            emit(Result.Failure("Connection Error",0))
        }
    }

    override suspend fun searchMoviesByKewWord(
        query: String,
        page: Int
    ): Flow<Result<List<Movie>>> {

        return flow {
            emit(Result.Loading())

            val result = moviesService.searchByKeyWord(kewWord = query, page = page)
            result.suspendOnSuccess {
                emit(Result.Success(this.data.results.toMovies()))
            }.suspendOnError {
                emit(Result.Failure(this.message(),this.statusCode.code))
            }.suspendOnException {
                emit(Result.Failure(this.message,0))
            }

        }.catch {
            emit(Result.Failure("Connection Error",0))
        }.flowOn(Dispatchers.IO)

    }
}