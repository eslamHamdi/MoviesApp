package com.eslam.moviesapp.domain.repositories

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

interface GetMoviesRepository {

    suspend fun getMoviesByGenre(genre:String,coroutineScope: CoroutineScope,genreTitle:String): Flow<Any>
}