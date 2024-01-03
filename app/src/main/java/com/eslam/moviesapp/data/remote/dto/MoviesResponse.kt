package com.eslam.moviesapp.data.remote.dto

import com.eslam.moviesapp.domain.models.Movie
import com.eslam.moviesapp.domain.models.Movies
import com.google.gson.annotations.SerializedName

data class MoviesResponse(

	@field:SerializedName("page")
	val page: Int? = null,

	@field:SerializedName("total_pages")
	val totalPages: Int? = null,

	@field:SerializedName("results")
	val results: List<ResultsItem?>? = null,

	@field:SerializedName("total_results")
	val totalResults: Int? = null
)

data class ResultsItem(

	@field:SerializedName("overview")
	val overview: String? = null,

	@field:SerializedName("original_language")
	val originalLanguage: String? = null,

	@field:SerializedName("original_title")
	val originalTitle: String? = null,

	@field:SerializedName("video")
	val video: Boolean? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("genre_ids")
	val genreIds: List<Int?>? = null,

	@field:SerializedName("poster_path")
	val posterPath: String? = null,

	@field:SerializedName("backdrop_path")
	val backdropPath: String? = null,

	@field:SerializedName("release_date")
	val releaseDate: String? = null,

	@field:SerializedName("popularity")
	val popularity: Any? = null,

	@field:SerializedName("vote_average")
	val voteAverage: Any? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("adult")
	val adult: Boolean? = null,

	@field:SerializedName("vote_count")
	val voteCount: Int? = null
)


  fun List<ResultsItem?>?.toMovie():List<Movie>
  {
	  return this?.map {
		  Movie(
			  it?.overview?:"", it?.title!!, it.genreIds?.get(0)!!,it.posterPath!!,
			  it.releaseDate!!.substringBefore("-"),it.voteAverage.toString(),it.id!!,it.voteCount!!)
	  }?: emptyList()
  }


  fun MoviesResponse.toMovies():Movies
  {

	  return Movies(this.results?.get(0)?.genreIds?.get(0)?.toString()!!, this.results.toMovie(),this.totalPages?:0)
  }
