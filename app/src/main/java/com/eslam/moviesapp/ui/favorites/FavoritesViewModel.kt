package com.eslam.moviesapp.ui.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eslam.moviesapp.domain.models.Movie
import com.eslam.moviesapp.domain.usecases.DeleteFavoriteUseCase
import com.eslam.moviesapp.domain.usecases.GetFavoritesUseCase
import com.eslam.moviesapp.domain.usecases.SaveFavoritesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class FavoritesViewModel @Inject constructor(private val getFavoritesUseCase: GetFavoritesUseCase,
private val deleteFavoriteUseCase: DeleteFavoriteUseCase,private val saveFavoritesUseCase: SaveFavoritesUseCase):ViewModel(){


    private var _moviesFlow: MutableStateFlow<List<Movie>> = MutableStateFlow(listOf())

    val movieFlow = _moviesFlow.asStateFlow()

    init {

        getAllFavorites()
    }

    private fun getAllFavorites()
    {
        viewModelScope.launch {
            getFavoritesUseCase().collect{

                _moviesFlow.value = it
            }
        }

    }

    fun deleteMovie(id: Int) {

        viewModelScope.launch {
            deleteFavoriteUseCase(id)
        }
    }

    fun addToFavorites(movie: Movie) {

        viewModelScope.launch {
            saveFavoritesUseCase(movie)
        }

    }

}