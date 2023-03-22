package com.eslam.moviesapp.ui.search.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.eslam.moviesapp.R
import com.eslam.moviesapp.databinding.FavoriteItemBinding
import com.eslam.moviesapp.domain.models.Movie

class SearchAdapter:ListAdapter<Movie, SearchAdapter.SearchedMoviesViewHolder>(SearchedMoviesDiffCallBack) {

    private val imageBaseUrl = "https://image.tmdb.org/t/p/original"

    lateinit var searchItemBinding: FavoriteItemBinding

    var movieSearchClick:MovieSearchClick?=null


    inner class SearchedMoviesViewHolder(private val searchItemBinding: FavoriteItemBinding):
        RecyclerView.ViewHolder(searchItemBinding.root)
    {
        fun bind(movie: Movie)
        {


            Glide.with(searchItemBinding.root.context)
                .load(imageBaseUrl+movie.posterPath)
                .placeholder(R.drawable.loading_animation)
                .error(R.drawable.ic_baseline_broken_image_24)
                .into(searchItemBinding.movieImage)

            searchItemBinding.favGener.text = movie.genre
            searchItemBinding.movieYear.text = "(${movie.releaseDate.substringBefore("-",movie.releaseDate)})"
            searchItemBinding.movieName.text = movie.title


            searchItemBinding.root.setOnClickListener {
                movieSearchClick?.onMovieClick(movie)
            }


        }
    }


    interface MovieSearchClick
    {
        fun onMovieClick(movie: Movie)
    }

    object SearchedMoviesDiffCallBack : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchedMoviesViewHolder {
        searchItemBinding = FavoriteItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return SearchedMoviesViewHolder(searchItemBinding)
    }

    override fun onBindViewHolder(holder: SearchedMoviesViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

}