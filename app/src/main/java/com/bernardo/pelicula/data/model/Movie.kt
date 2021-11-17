package com.bernardo.pelicula.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bernardo.pelicula.utils.AppConstants
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie(
    @Json(name = "id")
    val id: Int = 0,
    @Json(name = "original_language")
    val originalLanguage: String,
    @Json(name = "original_title")
    val originalTitle: String,
    @Json(name = "overview")
    val overview: String,
    @Json(name = "popularity")
    val popularity: Double,
    @Json(name = "poster_path")
    val posterPath: String,
    @Json(name = "release_date")
    val releaseDate: String,
    @Json(name = "title")
    val title: String,
    @Json(name = "vote_average")
    val voteAverage: Double,
    @Json(name = "vote_count")
    val voteCount: Int,
    @Json(name = "backdrop_path")
    val backdropPath: String
) : Parcelable

@Parcelize
data class MovieResponse(
    val results: List<Movie>
) : Parcelable

@Entity(tableName = AppConstants.TABLE_NAME)
data class MovieEntity(
    @PrimaryKey
    val id: Int,
    @ColumnInfo(name = "original_language")
    val originalLanguage: String = "",
    @ColumnInfo(name = "original_title")
    val originalTitle: String = "",
    @ColumnInfo(name = "overview")
    val overview: String = "",
    @ColumnInfo(name = "popularity")
    val popularity: Double = 0.0,
    @ColumnInfo(name = "poster_path")
    val posterPath: String = "",
    @ColumnInfo(name = "release_date")
    val releaseDate: String = "",
    @ColumnInfo(name = "title")
    val title: String = "",
    @ColumnInfo(name = "vote_average")
    val voteAverage: Double = 0.0,
    @ColumnInfo(name = "vote_count")
    val voteCount: Int = 0,
    @ColumnInfo(name = "backdrop_path")
    val backdropPath: String = ""
)