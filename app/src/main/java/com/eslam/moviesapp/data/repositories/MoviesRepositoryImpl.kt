package com.eslam.moviesapp.data.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.eslam.moviesapp.data.remote.MoviesPagingSource
import com.eslam.moviesapp.domain.models.Movie
import com.eslam.moviesapp.domain.repositories.GetMoviesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(private val remoteDataSource: RemoteDataSource):GetMoviesRepository {
    override suspend fun getMoviesByGenre(
        genre: String,
        coroutineScope: CoroutineScope
    ,genreTitle:String
    ):Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false, initialLoadSize = 40
            ),
            pagingSourceFactory = {
                MoviesPagingSource(remoteDataSource, genre,genreTitle)
            }
        ).flow.flowOn(Dispatchers.IO).cachedIn(coroutineScope)
    }
}