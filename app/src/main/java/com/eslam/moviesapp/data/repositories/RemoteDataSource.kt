package com.eslam.moviesapp.data.repositories

import com.eslam.moviesapp.domain.models.Genre
import com.eslam.moviesapp.domain.models.Movie
import kotlinx.coroutines.flow.Flow
import com.eslam.moviesapp.domain.models.Result

interface RemoteDataSource {

    suspend fun getGenres():Flow<Result<List<Genre>>>
    suspend fun getMoviesByGenre(genre:String,page:Int): Flow<Result<List<Movie>>>
    suspend fun searchMoviesByKewWord(query:String,page:Int): Flow<Result<List<Movie>>>
}