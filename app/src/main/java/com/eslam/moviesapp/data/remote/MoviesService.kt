package com.eslam.moviesapp.data.remote

import com.eslam.moviesapp.BuildConfig
import com.eslam.moviesapp.data.remote.dto.GenreResponse
import com.eslam.moviesapp.data.remote.dto.MoviesResponse
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesService {

    @GET("genre/movie/list")
    suspend fun getAllGenres(@Query("api_key") apiKey:String= BuildConfig.API_KEY,@Query("language") lang:String="en-US"):ApiResponse<GenreResponse>
    @GET("discover/movie")
    suspend fun getMoviesListByGenre(@Query("api_key") apiKey:String= BuildConfig.API_KEY,
                                     @Query("language") lang:String="en-US",
                                     @Query("sort_by") sortBy:String="popularity.desc",
                                     @Query("include_adult") adult:Boolean=false,
                                     @Query("include_video") video:Boolean=false, @Query("page") page:Int,
                                     @Query("with_genres") genre:String
    ):ApiResponse<MoviesResponse>

    @GET("search/movie")
    suspend fun searchByKeyWord(@Query("api_key") apiKey:String= BuildConfig.API_KEY,
                                @Query("language") lang:String="en-US",
                                @Query("include_adult") adult:Boolean=false,
                                @Query("page") page:Int=1,@Query("query") kewWord:String):ApiResponse<MoviesResponse>
}