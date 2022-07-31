package com.siuliano.emovies.network.endpoint

import com.siuliano.emovies.BuildConfig
import com.siuliano.emovies.model.configuration.ConfigurationDTO
import com.siuliano.emovies.model.movie.Movie
import com.siuliano.emovies.model.movie.MovieListDTO
import com.siuliano.emovies.utils.DateUtils
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TMDbApi {

    @GET("/3/configuration")
    suspend fun getConfiguration(
        @Query("api_key")
        apiKey: String = BuildConfig.API_KEY
    ): Response<ConfigurationDTO>

    @GET("/3/movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("api_key")
        apiKey: String = BuildConfig.API_KEY,
        @Query("page")
        page: Int = 1
    ): Response<MovieListDTO>

    @GET("/3/discover/movie")
    suspend fun getUpcomingMovies(
        @Query("api_key")
        apiKey: String = BuildConfig.API_KEY,
        @Query("sort_by")
        sortBy: String = "primary_release_date.asc",
        @Query("primary_release_date.gte")
        tomorrow: String = DateUtils.getTomorrowDate()
    ): Response<MovieListDTO>

    @GET("/3/discover/movie")
    suspend fun getRecommendedMoviesByYear(
        @Query("api_key")
        apiKey: String = BuildConfig.API_KEY,
        @Query("sort_by")
        sortBy: String = "popularity.desc",
        @Query("primary_release_year")
        year: Int
    ): Response<MovieListDTO>

    @GET("/3/discover/movie")
    suspend fun getRecommendedMoviesByLanguage(
        @Query("api_key")
        apiKey: String = BuildConfig.API_KEY,
        @Query("sort_by")
        sortBy: String = "popularity.desc",
        @Query("language")
        language: String
    ): Response<MovieListDTO>

    @GET("/3/movie/{movieId}")
    suspend fun getMovieDetails(
        @Path("movieId")
        movieId: Int,
        @Query("api_key")
        apiKey: String = BuildConfig.API_KEY,
        @Query("append_to_response")
        additionalData: String = "videos"
    ): Response<Movie>

}