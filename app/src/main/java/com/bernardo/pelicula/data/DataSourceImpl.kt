package com.bernardo.pelicula.data

import com.bernardo.pelicula.data.model.Movie
import com.bernardo.pelicula.data.model.MovieEntity
import com.bernardo.pelicula.domain.DataSource
import com.bernardo.pelicula.domain.MoviesDao
import com.bernardo.pelicula.domain.WebService
import com.bernardo.pelicula.vo.Resource
import javax.inject.Inject

class DataSourceImpl @Inject constructor(
    private val moviesDao: MoviesDao,
    private val webService: WebService
) : DataSource {

    override suspend fun getPopularMovies(
        page: Int,
        sortBy: String
    ): Resource<List<Movie>> {
        val response = Resource.Success(webService.getPopularMovies(page, sortBy, "es").results)

        response.data.forEach {
            moviesDao.insertMovie(MovieEntity(
                it.id,
                it.originalLanguage,
                it.originalTitle,
                it.overview,
                it.popularity,
                it.posterPath,
                it.releaseDate,
                it.title,
                it.voteAverage,
                it.voteCount,
                it.backdropPath,
            ))
        }

        return response
    }

    override suspend fun getMoviesSearch(search: String): Resource<List<Movie>> {
        var result = moviesDao.getSearchMovies(search)
        var response = result.map {
            Movie(
                it.id,
                it.originalLanguage,
                it.originalTitle,
                it.overview,
                it.popularity,
                it.posterPath,
                it.releaseDate,
                it.title,
                it.voteAverage,
                it.voteCount,
                it.backdropPath,
            )
        }
        return Resource.Success(response)
    }

    override suspend fun getMoviesPopular(): Resource<List<Movie>> {
        var result = moviesDao.getPopularMovies()
        var response = result.map {
            Movie(
                it.id,
                it.originalLanguage,
                it.originalTitle,
                it.overview,
                it.popularity,
                it.posterPath,
                it.releaseDate,
                it.title,
                it.voteAverage,
                it.voteCount,
                it.backdropPath,
            )
        }
        return Resource.Success(response)
    }

    override suspend fun getMoviesRated(): Resource<List<Movie>> {
        var result = moviesDao.getRatedMovies()
        var response = result.map {
            Movie(
                it.id,
                it.originalLanguage,
                it.originalTitle,
                it.overview,
                it.popularity,
                it.posterPath,
                it.releaseDate,
                it.title,
                it.voteAverage,
                it.voteCount,
                it.backdropPath,
            )
        }
        return Resource.Success(response)
    }

}