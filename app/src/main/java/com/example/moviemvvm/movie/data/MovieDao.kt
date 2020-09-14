package com.example.moviemvvm.movie.data

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MovieDao {

    @Query("SELECT * FROM movies WHERE title = :title")
    fun getMoviesByTitle(title: String): DataSource.Factory<Int, Movie>

    @Query("SELECT * FROM movies")
    fun getPagedMovies(): DataSource.Factory<Int, Movie>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list:List<Movie>)

}