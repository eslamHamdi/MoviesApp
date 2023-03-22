package com.eslam.moviesapp.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.eslam.moviesapp.domain.models.Movie

@Entity
data class MovieEntity(

    @PrimaryKey
    val id: Int,
    val overview: String,

    val title: String,

    val genreId: Int,

    val posterPath: String,

    val releaseDate: String,

    val voteAverage: String,
    val voteCount: Int,

    val isFavorite:Boolean=false,
    var genre:String)


  fun List<MovieEntity>.toMovie():List<Movie>
  {
     return this.map {
          Movie(it.overview,it.title,it.genreId,it.posterPath,it.releaseDate,it.voteAverage,it.id,it.voteCount,it.isFavorite,it.genre)
      }

  }