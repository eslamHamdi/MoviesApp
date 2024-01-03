package com.eslam.moviesapp.data.repositories

import com.eslam.moviesapp.domain.models.Genre
import com.eslam.moviesapp.domain.models.Movie
import com.eslam.moviesapp.domain.models.Movies
import com.eslam.moviesapp.domain.models.Result
import kotlinx.coroutines.flow.Flow

interface RemoteDataSource {

    suspend fun getGenres():Flow<Result<List<Genre>>>
    suspend fun getMoviesByGenre(genre:String,page:Int): Movies?
    suspend fun searchMoviesByKewWord(query:String,page:Int): Flow<Result<List<Movie>>>
}