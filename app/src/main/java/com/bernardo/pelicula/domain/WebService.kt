package com.bernardo.pelicula.domain

import com.bernardo.pelicula.data.model.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WebService {

    @GET("list/1")
    suspend fun getPopularMovies(@Query("page") page: Int, @Query("sort_by") sortBy: String, @Query("language") language: String): MovieResponse

}