package com.eslam.moviesapp.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Movie(
    val overview: String,

    val title: String,

    val genreId: Int,

    val posterPath: String,

    val releaseDate: String,

    val voteAverage: String,

    val id: Int,

    val voteCount: Int,

    var isFavorite:Boolean=false,
    var genre:String=""
) : Parcelable
