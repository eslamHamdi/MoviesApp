package com.eslam.moviesapp.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eslam.moviesapp.domain.models.Movie
import com.eslam.moviesapp.domain.models.Movies
import com.eslam.moviesapp.domain.models.Result
import com.eslam.moviesapp.domain.usecases.MovieSearchUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SearchViewModel @Inject constructor(private val movieSearchUseCase: MovieSearchUseCase):ViewModel() {

    private var _moviesFlow: MutableStateFlow<List<Movie>> = MutableStateFlow(listOf())

    val movieFlow = _moviesFlow.asStateFlow()

    private var _loadingState = MutableStateFlow(false)

    val loadingState = _loadingState.asStateFlow()


    private val errorMsg = Channel<String>(Channel.BUFFERED)
    val errorFlow = errorMsg.receiveAsFlow()


    fun moviesSearch(keyWord: String) {
    viewModelScope.launch {
        movieSearchUseCase.invoke(keyWord).collect{
            when(it)
            {
                is Result.Success->{

                    _moviesFlow.value = it.value


                    _loadingState.value = false
                }
                is Result.Failure -> {
                    _loadingState.value = false
                    errorMsg.send(it.message?:"Failure")
                }
                is Result.Loading -> {
                    _loadingState.value = true
                }
            }
        }
}


    }


    fun resetSearch()
    {
        _moviesFlow.value = listOf()
    }
}