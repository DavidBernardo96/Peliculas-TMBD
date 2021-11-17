package com.bernardo.pelicula.ui.viewmodel

import androidx.lifecycle.*
import com.bernardo.pelicula.domain.Repo
import com.bernardo.pelicula.vo.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
        private val repo: Repo,
) : ViewModel() {

    private val titleMovie = MutableLiveData<String>()

    fun setMovie(title: String) {
        titleMovie.value = title
    }

    val populares = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            val result = repo.getMoviesList(
                1,
                "popularity.desc"
            )
            emit(result)
        } catch (e: Exception) {
            emit(Resource.Failure(e))
            emit(
                repo.getMoviesPopular()
            )
        }
    }

    val rateds = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            val result = repo.getMoviesList(
                1,
                "vote_average.desc"
            )
            emit(result)
        } catch (e: Exception) {
            emit(Resource.Failure(e))
            emit(
                repo.getMoviesRated()
            )
        }
    }

    val fetchMoviesSearchList = titleMovie.distinctUntilChanged().switchMap { queryMovie ->
        liveData(Dispatchers.IO) {
            emit(Resource.Loading())
            try {
                emit(
                    repo.getMoviesSearch(
                        queryMovie
                    )
                )
            } catch (e: Exception) {
                emit(Resource.Failure(e))
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        setMovie("")
    }
}