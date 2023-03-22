package com.eslam.moviesapp.domain.usecases

import com.eslam.moviesapp.domain.repositories.MovieSearchRepository
import javax.inject.Inject

class MovieSearchUseCase @Inject constructor(private val movieSearchRepository: MovieSearchRepository) {

    suspend operator fun invoke(query:String)=movieSearchRepository.getMoviesByKeyWord(query)
}