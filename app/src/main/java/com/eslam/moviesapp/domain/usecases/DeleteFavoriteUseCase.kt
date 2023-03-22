package com.eslam.moviesapp.domain.usecases

import com.eslam.moviesapp.domain.repositories.FavoritesRepository
import javax.inject.Inject

class DeleteFavoriteUseCase @Inject constructor(private val favoritesRepository: FavoritesRepository) {

    suspend operator fun invoke(id:Int)=favoritesRepository.deleteFavorite(id)
}