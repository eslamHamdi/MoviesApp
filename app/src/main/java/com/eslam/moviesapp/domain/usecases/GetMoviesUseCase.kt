package com.eslam.moviesapp.domain.usecases

import com.eslam.moviesapp.domain.repositories.GetMoviesRepository
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

class GetMoviesUseCase @Inject constructor(private val getMoviesRepository: GetMoviesRepository) {

    suspend operator fun invoke(genre:String,coroutineScope: CoroutineScope,genreTitle:String)=
        getMoviesRepository.getMoviesByGenre(genre = genre,coroutineScope,genreTitle)
}