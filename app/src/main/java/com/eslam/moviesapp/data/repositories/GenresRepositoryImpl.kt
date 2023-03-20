package com.eslam.moviesapp.data.repositories

import com.eslam.moviesapp.domain.models.Genre
import com.eslam.moviesapp.domain.models.Result
import com.eslam.moviesapp.domain.repositories.GetGenresRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GenresRepositoryImpl @Inject constructor(private val remoteDataSource: RemoteDataSource):GetGenresRepository {
    override suspend fun getGenres(): Flow<Result<List<Genre>>> {
        return remoteDataSource.getGenres()
    }
}