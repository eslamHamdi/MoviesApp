package com.eslam.moviesapp.domain.usecases

import com.eslam.moviesapp.domain.repositories.GetMoviesRepository
import javax.inject.Inject

class GetMoviesUseCase @Inject constructor(private val getMoviesRepository: GetMoviesRepository) {

    suspend operator fun invoke(genre:String)=getMoviesRepository.getMoviesByGenre(genre = genre)
}