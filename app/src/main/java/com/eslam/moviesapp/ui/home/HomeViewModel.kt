package com.eslam.moviesapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.eslam.moviesapp.domain.models.Genre
import com.eslam.moviesapp.domain.models.Movie
import com.eslam.moviesapp.domain.models.Result
import com.eslam.moviesapp.domain.usecases.GetGenresUseCase
import com.eslam.moviesapp.domain.usecases.GetMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(private val getGenresUseCase: GetGenresUseCase,
                                        private val getMoviesUseCase: GetMoviesUseCase):ViewModel()  {


    private var _genresFlow:MutableStateFlow<List<Genre>> = MutableStateFlow(listOf())

    val genreFlow = _genresFlow.asStateFlow()

    private var _moviesFlow:MutableStateFlow<Pair<PagingData<Movie>,String>?> = MutableStateFlow(null)

    val movieFlow = _moviesFlow.asStateFlow()

    private var _filteredMoviesFlow:MutableStateFlow<Pair<PagingData<Movie>,String>?> = MutableStateFlow(null)

    val filteredMoviesFlow = _filteredMoviesFlow.asStateFlow()


    private var _loadingState = MutableStateFlow(false)

    val loadingState = _loadingState.asStateFlow()


    private val errorMsg = Channel<String>(Channel.BUFFERED)
    val errorFlow = errorMsg.receiveAsFlow()



    fun getGenres()
    {
        viewModelScope.launch {
            getGenresUseCase.invoke().collect{
                when(it)
                {
                    is Result.Success->{

                        _genresFlow.value = it.value
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



   suspend fun getMovies(generId:Int,genreTitle:String)
    {

          getMoviesUseCase(generId.toString(),viewModelScope,genreTitle).catch { exception->
              errorMsg.send(exception.toString())
          }.collect{
              _moviesFlow.value = Pair(it as PagingData<Movie>,genreTitle)
          }

    }

   suspend fun filterMovies(generId:Int,genreTitle:String)
    {

        getMoviesUseCase(generId.toString(),viewModelScope,genreTitle).catch { exception->
            errorMsg.send(exception.toString())
        }.collect{
            _filteredMoviesFlow.value = Pair(it as PagingData<Movie>,genreTitle)
        }
    }

    fun resetList()
    {
        _moviesFlow.value = null
    }

}