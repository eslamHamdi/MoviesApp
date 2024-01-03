package com.eslam.moviesapp.ui.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.eslam.moviesapp.R
import com.eslam.moviesapp.databinding.MovieItemBinding
import com.eslam.moviesapp.domain.models.Movie


class NestedMoviesAdapter : PagingDataAdapter<Movie, NestedMoviesAdapter.NestedMoviesViewHolder>(NestedMoviesDiffCallBack){


    private val imageBaseUrl = "https://image.tmdb.org/t/p/original"

    lateinit var movieItemBinding: MovieItemBinding

    var movieClick:MovieClick?=null


    inner class NestedMoviesViewHolder(private val movieItemBinding: MovieItemBinding):ViewHolder(movieItemBinding.root)
    {
        fun bind(movie: Movie)
        {


            Glide.with(movieItemBinding.root.context)
                .load(imageBaseUrl+movie.posterPath)
                .placeholder(R.drawable.loading_animation)
                .error(R.drawable.ic_baseline_broken_image_24)
                .into(movieItemBinding.moviePoster)

            movieItemBinding.gener.text = movie.genre
            movieItemBinding.year.text = "(${movie.releaseDate.substringBefore("-",movie.releaseDate)})"

            movieItemBinding.root.setOnClickListener {
                movieClick?.onMovieClick(movie)
            }


        }
    }


    interface MovieClick
    {
        fun onMovieClick(movie:Movie)
    }


    object NestedMoviesDiffCallBack : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NestedMoviesViewHolder {
        movieItemBinding = MovieItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return NestedMoviesViewHolder(movieItemBinding)
    }

    override fun onBindViewHolder(holder: NestedMoviesViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem!!)
    }


}