package com.eslam.moviesapp.ui.favorites.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.eslam.moviesapp.R
import com.eslam.moviesapp.databinding.FavoriteItemBinding
import com.eslam.moviesapp.domain.models.Movie

class FavoritesAdapter:ListAdapter<Movie,FavoritesAdapter.FavoriteMoviesViewHolder>(FavoriteMoviesDiffCallBack){

    private val imageBaseUrl = "https://image.tmdb.org/t/p/original"

    lateinit var searchItemBinding: FavoriteItemBinding

    var favoriteMovieClick:FavoriteMovieSearchClick?=null


    inner class FavoriteMoviesViewHolder(private val searchItemBinding: FavoriteItemBinding):
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
                favoriteMovieClick?.onMovieClick(movie)
            }


        }
    }


    interface FavoriteMovieSearchClick
    {
        fun onMovieClick(movie: Movie)
    }

    object FavoriteMoviesDiffCallBack : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteMoviesViewHolder {
        searchItemBinding = FavoriteItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return FavoriteMoviesViewHolder(searchItemBinding)
    }

    override fun onBindViewHolder(holder: FavoriteMoviesViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

}