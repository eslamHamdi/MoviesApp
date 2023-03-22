package com.eslam.moviesapp.data.local.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.eslam.moviesapp.data.local.entities.MovieEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface FavoriteMovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveMovie(movieEntity: MovieEntity)

    @Query("Select * From MovieEntity")
    fun getAllFavorites(): Flow<List<MovieEntity>>

    @Query("Delete From MovieEntity Where id = :movieId")
    fun deleteMovie(movieId:Int)
}