package com.eslam.moviesapp.domain.models

data class Movies(
    val genre:String,
    val movieList:List<Movie>,
    val totalPages:Int

)
