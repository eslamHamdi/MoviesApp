package com.eslam.moviesapp.ui.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.eslam.moviesapp.databinding.MovieCategoryLayoutBinding
import com.eslam.moviesapp.domain.models.Movie
import com.eslam.moviesapp.domain.models.Movies
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class MoviesAdapter:ListAdapter<Movies,MoviesAdapter.MoviesViewHolder>(MoviesDiffCallBack),NestedMoviesAdapter.MovieClick {

    private lateinit var movieCategoryLayoutBinding: MovieCategoryLayoutBinding
    private var _selectMovieFlow:MutableStateFlow<Movie?> = MutableStateFlow(null)
    private val nestedMoviesAdapter:NestedMoviesAdapter = NestedMoviesAdapter()
    private var pagingData:PagingData<Movie>?=null
    private var scope:CoroutineScope?=null
    val selectMovieFlow = _selectMovieFlow.asStateFlow()

    inner class MoviesViewHolder(private val movieCategoryLayoutBinding: MovieCategoryLayoutBinding)
        :ViewHolder(movieCategoryLayoutBinding.root)
    {
            fun bind(movies: Movies)
            {
                movieCategoryLayoutBinding.categoryTitle.text = movies.genre
                val moviesList= movies.movieList.onEach {
                    it.genre = movies.genre
                }

                movieCategoryLayoutBinding.categoryRecycler.adapter = nestedMoviesAdapter
                nestedMoviesAdapter.movieClick = this@MoviesAdapter
                scope?.launch {
                    nestedMoviesAdapter.submitData(pagingData!!)
                }




            }

            }

    object MoviesDiffCallBack : DiffUtil.ItemCallback<Movies>() {
        override fun areItemsTheSame(oldItem: Movies, newItem: Movies): Boolean {
            return oldItem.genre == newItem.genre
        }

        override fun areContentsTheSame(oldItem: Movies, newItem: Movies): Boolean {
            return oldItem == newItem
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        movieCategoryLayoutBinding = MovieCategoryLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MoviesViewHolder(movieCategoryLayoutBinding)
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    override fun onMovieClick(movie: Movie) {
        _selectMovieFlow.value = movie
    }


   fun setNestedPagingData(pagingData: PagingData<Movie>,scope: CoroutineScope)
    {
        this.pagingData = pagingData
        this.scope = scope


    }


}