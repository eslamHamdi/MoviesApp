package com.eslam.moviesapp.domain.usecases

import com.eslam.moviesapp.domain.repositories.FavoritesRepository
import javax.inject.Inject

class GetFavoritesUseCase @Inject constructor(private val favoritesRepository: FavoritesRepository) {
    operator fun invoke()=favoritesRepository.getFavorites()
}