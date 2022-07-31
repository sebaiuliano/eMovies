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
    val voteAverage: Double,
    val videos: Videos
)

@JsonClass(generateAdapter = true)
data class PaginatedMovieList(
    val page: Int,
    val results: List<MovieMinimalData>
)

@JsonClass(generateAdapter = true)
data class MovieMinimalData(
    val id: Int,
    @Json(name = "genre_ids")
    val genres: List<Int>,
    val title: String,
    @Json(name = "release_date")
    val releaseDate: String,
    @Json(name = "poster_path")
    val posterPath: String?
)

@JsonClass(generateAdapter = true)
data class Genre(
    val id: Int,
    val name: String
)

@JsonClass(generateAdapter = true)
data class Videos(
    val results: List<Video>
)

@JsonClass(generateAdapter = true)
data class Video(
    val key: String,
    val site: String,
    val type: String,
    val official: Boolean
)