package com.bernardo.pelicula.domain

import com.bernardo.pelicula.data.model.Movie
import com.bernardo.pelicula.vo.Resource
import javax.inject.Inject

class RepoImpl @Inject constructor(private val dataSource: DataSource) : Repo {
    override suspend fun getMoviesList(page: Int, sortBy: String): Resource<List<Movie>> {
        return dataSource.getPopularMovies(page, sortBy)
    }

    override suspend fun getMoviesSearch(search: String): Resource<List<Movie>> {
        return dataSource.getMoviesSearch(search)
    }

    override suspend fun getMoviesPopular(): Resource<List<Movie>> {
        return dataSource.getMoviesPopular()
    }

    override suspend fun getMoviesRated(): Resource<List<Movie>> {
        return dataSource.getMoviesRated()
    }
}