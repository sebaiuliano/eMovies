package com.siuliano.emovies.model.movie

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Movie(
    val id: Int,
    val genres: List<Genre>,
    @Json(name = "original_language")
    val originalLanguage: String,
    @Json(name = "original_title")
    val originalTitle: String,
    val overview: String,
    @Json(name = "poster_path")
    val posterPath: String,
    @Json(name = "release_date")
    val releaseDate: String,
    val title: String,
    @Json(name = "vote_average")
    val voteAverage: Double
)

@JsonClass(generateAdapter = true)
data class PaginatedMovieList(
    val page: Int,
    val results: List<MovieMinimalData>
)

@JsonClass(generateAdapter = true)
data class MovieMinimalData(
    val id: Int,
    val genres: List<Int>,
    val title: String,
    @Json(name = "release_date")
    val releaseDate: String,
    @Json(name = "poster_path")
    val posterPath: String
)

@JsonClass(generateAdapter = true)
data class Genre(
    val id: Int,
    val name: String
)