package com.eslam.moviesapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.eslam.moviesapp.data.local.daos.FavoriteMovieDao
import com.eslam.moviesapp.data.local.entities.MovieEntity


@Database(entities = [MovieEntity::class],
    version = 1, exportSchema = false)
abstract class MoviesDataBase: RoomDatabase() {


    abstract fun getFavoritesDao():FavoriteMovieDao

}