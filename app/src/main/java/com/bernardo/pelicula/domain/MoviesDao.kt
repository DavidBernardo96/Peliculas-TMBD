package com.bernardo.pelicula.domain

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bernardo.pelicula.data.model.MovieEntity

@Dao
interface MoviesDao {

    @Query("SELECT * FROM table_movies")
    suspend fun getAllMovies():List<MovieEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie:MovieEntity)


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMoviess(movies: List<MovieEntity>)

    @Query("SELECT * FROM table_movies WHERE title LIKE '%' || :query || '%'")
    suspend fun getSearchMovies(query:String):List<MovieEntity>

    @Query("SELECT * FROM table_movies ORDER BY popularity DESC")
    suspend fun getPopularMovies():List<MovieEntity>

    @Query("SELECT * FROM table_movies ORDER BY vote_average DESC")
    suspend fun getRatedMovies():List<MovieEntity>


}