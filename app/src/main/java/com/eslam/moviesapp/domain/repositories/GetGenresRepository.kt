package com.eslam.moviesapp.domain.repositories

import com.eslam.moviesapp.domain.models.Genre
import com.eslam.moviesapp.domain.models.Result
import kotlinx.coroutines.flow.Flow

interface GetGenresRepository {

    suspend fun getGenres():Flow<Result<List<Genre>>>


}