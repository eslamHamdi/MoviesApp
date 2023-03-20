package com.eslam.moviesapp.domain.usecases

import com.eslam.moviesapp.domain.repositories.GetGenresRepository
import javax.inject.Inject

class GetGenresUseCase @Inject constructor(private val getGenresRepository: GetGenresRepository) {

    suspend operator fun invoke()=getGenresRepository.getGenres()
}