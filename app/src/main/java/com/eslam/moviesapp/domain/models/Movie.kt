package com.eslam.moviesapp.domain.models

data class Movie(
    val overview: String,

    val title: String,

    val genreId: Int,

    val posterPath: String,

    val releaseDate: String,

    val voteAverage: String,

    val id: Int,

    val voteCount: Int
)
