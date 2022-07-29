package com.siuliano.emovies.network.endpoint

import com.siuliano.emovies.BuildConfig
import com.siuliano.emovies.model.Movie
import com.siuliano.emovies.model.PaginatedMovieList
import com.siuliano.emovies.utils.DateUtils
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TMDbApi {

    @GET("/3/movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("api_key")
        apiKey: String = BuildConfig.API_KEY
    ): Response<PaginatedMovieList>

    @GET("/3/discover/movie")
    suspend fun getUpcomingMovies(
        @Query("api_key")
        apiKey: String = BuildConfig.API_KEY,
        @Query("sort_by")
        sortBy: String = "primary_release_date.asc",
        @Query("primary_release_date.gte")
        tomorrow: String = DateUtils.getTomorrowDate()
    ): Response<PaginatedMovieList>

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