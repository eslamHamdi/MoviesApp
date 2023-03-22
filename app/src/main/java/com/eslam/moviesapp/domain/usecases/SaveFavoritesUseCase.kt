package com.eslam.moviesapp.domain.usecases

import com.eslam.moviesapp.domain.models.Movie
import com.eslam.moviesapp.domain.repositories.FavoritesRepository
import javax.inject.Inject

class SaveFavoritesUseCase @Inject constructor(private val favoritesRepository: FavoritesRepository){

   suspend operator fun invoke(movie: Movie)=favoritesRepository.saveFavorite(movie)


}