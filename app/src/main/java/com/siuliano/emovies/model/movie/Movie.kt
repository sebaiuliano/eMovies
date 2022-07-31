package com.siuliano.emovies.model.movie

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Movie(
    val id: Int,
    val genres: List<Genre>,
    @Json(name = "original_language")
    val originalLanguage: String,
    val overview: String,
    @Json(name = "poster_path")
    val posterPath: String?,
    @Json(name = "release_date")
    val releaseDate: String,
    val title: String,
    @Json(name = "vote_average")
    val voteAverage: Double,
    val videos: Videos
)

@JsonClass(generateAdapter = true)
data class MovieListDTO(
    val page: Int,
    val results: List<MovieDTO>,
    @Json(name = "total_pages")
    val totalPages: Int
)

@JsonClass(generateAdapter = true)
data class MovieDTO(
    val id: Int,
    @Json(name = "genre_ids")
    val genres: List<Int>,
    val title: String,
    @Json(name = "release_date")
    val releaseDate: String,
    @Json(name = "poster_path")
    val posterPath: String?,
    val overview: String,
    @Json(name = "vote_average")
    val voteAverage: Double,
    @Json(name = "original_language")
    val originalLanguage: String,
)

fun MovieDTO.toMovie() = Movie(
    id = id,
    genres = emptyList(),
    originalLanguage = originalLanguage,
    overview = overview,
    posterPath = posterPath,
    releaseDate = releaseDate,
    title = title,
    voteAverage = voteAverage,
    videos = Videos(emptyList())
)