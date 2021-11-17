package com.bernardo.pelicula.domain

import com.bernardo.pelicula.data.model.Movie
import com.bernardo.pelicula.vo.Resource

interface DataSource {
    suspend fun getPopularMovies(page: Int, sortBy: String): Resource<List<Movie>>
    suspend fun getMoviesSearch(search: String): Resource<List<Movie>>
    suspend fun getMoviesPopular(): Resource<List<Movie>>
    suspend fun getMoviesRated(): Resource<List<Movie>>
}