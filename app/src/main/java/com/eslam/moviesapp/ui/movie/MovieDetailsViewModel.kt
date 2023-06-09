package com.eslam.moviesapp.ui.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eslam.moviesapp.domain.models.Movie
import com.eslam.moviesapp.domain.usecases.DeleteFavoriteUseCase
import com.eslam.moviesapp.domain.usecases.SaveFavoritesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MovieDetailsViewModel @Inject constructor(private val saveFavoritesUseCase: SaveFavoritesUseCase,
                                                private val deleteFavoriteUseCase: DeleteFavoriteUseCase ) :ViewModel(){

    fun saveMovie(movie: Movie)
    {
        viewModelScope.launch {
            saveFavoritesUseCase(movie)
        }
    }

    fun deleteMovie(id:Int)
    {
        viewModelScope.launch {
            deleteFavoriteUseCase(id)
        }
    }
}