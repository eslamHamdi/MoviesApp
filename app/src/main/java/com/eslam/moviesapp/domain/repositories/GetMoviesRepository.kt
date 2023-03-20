package com.eslam.moviesapp.domain.repositories

import com.eslam.moviesapp.domain.models.Movie
import com.eslam.moviesapp.domain.models.Result
import kotlinx.coroutines.flow.Flow

interface GetMoviesRepository {

    suspend fun getMoviesByGenre( page:Int=1,
                                 genre:String): Flow<Result<List<Movie>>>
}