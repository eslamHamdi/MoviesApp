package com.eslam.moviesapp.domain.repositories

import com.eslam.moviesapp.domain.models.Movie
import com.eslam.moviesapp.domain.models.Result
import kotlinx.coroutines.flow.Flow

interface MovieSearchRepository {

    suspend fun getMoviesByKeyWord(query:String): Flow<Result<List<Movie>>>

}